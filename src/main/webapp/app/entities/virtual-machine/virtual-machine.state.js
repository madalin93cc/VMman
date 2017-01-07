(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('virtual-machine', {
            parent: 'entity',
            url: '/virtual-machine?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'VirtualMachines'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/virtual-machine/virtual-machines.html',
                    controller: 'VirtualMachineController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                account: ['Principal', function (Principal) {
                    return Principal.identity().then(function(account) {
                        return account;
                    });
                }]
            }
        })
        .state('virtual-machine-detail', {
            parent: 'entity',
            url: '/virtual-machine/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'VirtualMachine'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/virtual-machine/virtual-machine-detail.html',
                    controller: 'VirtualMachineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VirtualMachine', function($stateParams, VirtualMachine) {
                    return VirtualMachine.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'virtual-machine',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }],
                account: ['Principal', function (Principal) {
                    return Principal.identity().then(function(account) {
                        return account;
                    });
                }]
            }
        })
        .state('virtual-machine-detail.edit', {
            parent: 'virtual-machine-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/virtual-machine/virtual-machine-dialog.html',
                    controller: 'VirtualMachineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VirtualMachine', function(VirtualMachine) {
                            return VirtualMachine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('virtual-machine.new', {
            parent: 'virtual-machine',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/virtual-machine/virtual-machine-dialog.html',
                    controller: 'VirtualMachineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                environment: null,
                                ip: null,
                                hdd: null,
                                processor: null,
                                ram: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('virtual-machine', null, { reload: 'virtual-machine' });
                }, function() {
                    $state.go('virtual-machine');
                });
            }]
        })
        .state('virtual-machine.edit', {
            parent: 'virtual-machine',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/virtual-machine/virtual-machine-dialog.html',
                    controller: 'VirtualMachineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VirtualMachine', function(VirtualMachine) {
                            return VirtualMachine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('virtual-machine', null, { reload: 'virtual-machine' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('virtual-machine.delete', {
            parent: 'virtual-machine',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/virtual-machine/virtual-machine-delete-dialog.html',
                    controller: 'VirtualMachineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VirtualMachine', function(VirtualMachine) {
                            return VirtualMachine.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('virtual-machine', null, { reload: 'virtual-machine' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
