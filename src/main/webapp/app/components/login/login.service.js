(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .factory('LoginService', LoginService);

    LoginService.$inject = ['$uibModal', '$state'];

    function LoginService ($uibModal, $state) {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open () {
            // if (modalInstance !== null) return;
            // modalInstance = $uibModal.open({
            //     animation: true,
            //     templateUrl: 'app/components/login/login.html',
            //     controller: 'LoginController',
            //     controllerAs: 'vm'
            // });
            // modalInstance.result.then(
            //     resetModal,
            //     resetModal
            // );
            $state.go('login');
        }
    }
})();
