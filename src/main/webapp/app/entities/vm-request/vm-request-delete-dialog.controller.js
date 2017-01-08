(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VmRequestDeleteController',VmRequestDeleteController);

    VmRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'VmRequest'];

    function VmRequestDeleteController($uibModalInstance, entity, VmRequest) {
        var vm = this;

        vm.vmRequest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VmRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
