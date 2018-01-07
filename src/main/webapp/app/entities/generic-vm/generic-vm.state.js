(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('generic-vm', {
            parent: 'entity',
            url: '/generic-vm?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'GenericVms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/generic-vm/generic-vms.html',
                    controller: 'GenericVmController',
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
                }]
            }
        })
        .state('generic-vm-detail', {
            parent: 'generic-vm',
            url: '/generic-vm/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'GenericVm'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/generic-vm/generic-vm-detail.html',
                    controller: 'GenericVmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GenericVm', function($stateParams, GenericVm) {
                    return GenericVm.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'generic-vm',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('generic-vm-detail.edit', {
            parent: 'generic-vm-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/generic-vm/generic-vm-dialog.html',
                    controller: 'GenericVmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GenericVm', function(GenericVm) {
                            return GenericVm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('generic-vm.new', {
            parent: 'generic-vm',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/generic-vm/generic-vm-dialog.html',
                    controller: 'GenericVmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                hdd: null,
                                processor: null,
                                ram: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('generic-vm', null, { reload: 'generic-vm' });
                }, function() {
                    $state.go('generic-vm');
                });
            }]
        })
        .state('generic-vm.edit', {
            parent: 'generic-vm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/generic-vm/generic-vm-dialog.html',
                    controller: 'GenericVmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GenericVm', function(GenericVm) {
                            return GenericVm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('generic-vm', null, { reload: 'generic-vm' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('generic-vm.delete', {
            parent: 'generic-vm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/generic-vm/generic-vm-delete-dialog.html',
                    controller: 'GenericVmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GenericVm', function(GenericVm) {
                            return GenericVm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('generic-vm', null, { reload: 'generic-vm' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
