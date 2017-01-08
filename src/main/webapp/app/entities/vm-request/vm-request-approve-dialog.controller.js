(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VmRequestApproveController',VmRequestApproveController);

    VmRequestApproveController.$inject = ['$uibModalInstance', 'entity', '$http'];

    function VmRequestApproveController($uibModalInstance, entity, $http) {
        var vm = this;

        vm.vmRequest = entity;
        vm.clear = clear;
        vm.confirmApprove = confirmApprove;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmApprove (id) {
            $http.put('api/vm-requests/' + id)
                .then(function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
