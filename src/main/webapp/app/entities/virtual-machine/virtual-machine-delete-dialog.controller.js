(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VirtualMachineDeleteController',VirtualMachineDeleteController);

    VirtualMachineDeleteController.$inject = ['$uibModalInstance', 'entity', 'VirtualMachine'];

    function VirtualMachineDeleteController($uibModalInstance, entity, VirtualMachine) {
        var vm = this;

        vm.virtualMachine = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VirtualMachine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
