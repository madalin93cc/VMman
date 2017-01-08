(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vm-request', {
            parent: 'entity',
            url: '/vm-request?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'VmRequests'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vm-request/vm-requests.html',
                    controller: 'VmRequestController',
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
        .state('vm-request-detail', {
            parent: 'entity',
            url: '/vm-request/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'VmRequest'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vm-request/vm-request-detail.html',
                    controller: 'VmRequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VmRequest', function($stateParams, VmRequest) {
                    return VmRequest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vm-request',
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
        .state('vm-request-detail.edit', {
            parent: 'vm-request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vm-request/vm-request-dialog.html',
                    controller: 'VmRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VmRequest', function(VmRequest) {
                            return VmRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vm-request.new', {
            parent: 'vm-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vm-request/vm-request-dialog.html',
                    controller: 'VmRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                hdd: null,
                                processor: null,
                                ram: null,
                                approved: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vm-request', null, { reload: 'vm-request' });
                }, function() {
                    $state.go('vm-request');
                });
            }]
        })
        .state('vm-request.edit', {
            parent: 'vm-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vm-request/vm-request-dialog.html',
                    controller: 'VmRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VmRequest', function(VmRequest) {
                            return VmRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vm-request', null, { reload: 'vm-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vm-request.delete', {
            parent: 'vm-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vm-request/vm-request-delete-dialog.html',
                    controller: 'VmRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VmRequest', function(VmRequest) {
                            return VmRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vm-request', null, { reload: 'vm-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vm-request.approve', {
            parent: 'vm-request',
            url: '/{id}/approve',
            data: {
                authorities: ['ROLE_MANAGER', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vm-request/vm-request-approve-dialog.html',
                    controller: 'VmRequestApproveController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VmRequest', function(VmRequest) {
                            return VmRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vm-request', null, { reload: 'vm-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });

    }

})();
