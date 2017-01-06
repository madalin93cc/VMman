(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VirtualMachineDialogController', VirtualMachineDialogController);

    VirtualMachineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VirtualMachine', 'Project', 'OperatingSystem'];

    function VirtualMachineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VirtualMachine, Project, OperatingSystem) {
        var vm = this;

        vm.virtualMachine = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.operatingsystems = OperatingSystem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.virtualMachine.id !== null) {
                VirtualMachine.update(vm.virtualMachine, onSaveSuccess, onSaveError);
            } else {
                VirtualMachine.save(vm.virtualMachine, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:virtualMachineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
