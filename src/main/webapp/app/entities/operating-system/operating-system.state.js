(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('operating-system', {
            parent: 'entity',
            url: '/operating-system?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OperatingSystems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/operating-system/operating-systems.html',
                    controller: 'OperatingSystemController',
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
            }
        })
        .state('operating-system-detail', {
            parent: 'entity',
            url: '/operating-system/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OperatingSystem'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/operating-system/operating-system-detail.html',
                    controller: 'OperatingSystemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OperatingSystem', function($stateParams, OperatingSystem) {
                    return OperatingSystem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'operating-system',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('operating-system-detail.edit', {
            parent: 'operating-system-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operating-system/operating-system-dialog.html',
                    controller: 'OperatingSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OperatingSystem', function(OperatingSystem) {
                            return OperatingSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('operating-system.new', {
            parent: 'operating-system',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operating-system/operating-system-dialog.html',
                    controller: 'OperatingSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('operating-system', null, { reload: 'operating-system' });
                }, function() {
                    $state.go('operating-system');
                });
            }]
        })
        .state('operating-system.edit', {
            parent: 'operating-system',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operating-system/operating-system-dialog.html',
                    controller: 'OperatingSystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OperatingSystem', function(OperatingSystem) {
                            return OperatingSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('operating-system', null, { reload: 'operating-system' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('operating-system.delete', {
            parent: 'operating-system',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operating-system/operating-system-delete-dialog.html',
                    controller: 'OperatingSystemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OperatingSystem', function(OperatingSystem) {
                            return OperatingSystem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('operating-system', null, { reload: 'operating-system' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
