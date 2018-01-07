(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('GenericVmDeleteController',GenericVmDeleteController);

    GenericVmDeleteController.$inject = ['$uibModalInstance', 'entity', 'GenericVm'];

    function GenericVmDeleteController($uibModalInstance, entity, GenericVm) {
        var vm = this;

        vm.genericVm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GenericVm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
