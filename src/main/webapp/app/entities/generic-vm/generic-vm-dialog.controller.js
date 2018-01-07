(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('GenericVmDialogController', GenericVmDialogController);

    GenericVmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GenericVm', 'VirtualMachine', 'VmRequest'];

    function GenericVmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GenericVm, VirtualMachine, VmRequest) {
        var vm = this;

        vm.genericVm = entity;
        vm.clear = clear;
        vm.save = save;
        vm.virtualmachines = VirtualMachine.query();
        vm.vmrequests = VmRequest.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.genericVm.id !== null) {
                GenericVm.update(vm.genericVm, onSaveSuccess, onSaveError);
            } else {
                GenericVm.save(vm.genericVm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:genericVmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
