(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('password', {
            parent: 'account',
            url: '/password',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Password'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
