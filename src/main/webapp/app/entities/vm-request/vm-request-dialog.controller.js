(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VmRequestDialogController', VmRequestDialogController);

    VmRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VmRequest', 'OperatingSystem', 'Project', 'GenericVm'];

    function VmRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VmRequest, OperatingSystem, Project, GenericVm) {
        var vm = this;
        vm.vmRequest = entity;
        vm.clear = clear;
        vm.save = save;
        vm.operatingsystems = OperatingSystem.query();
        vm.projects = Project.query();
        vm.genericVms = GenericVm.query();
        vm.onInstanceChange = onInstanceChange;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vmRequest.id !== null) {
                VmRequest.update(vm.vmRequest, onSaveSuccess, onSaveError);
            } else {
                VmRequest.save(vm.vmRequest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:vmRequestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function onInstanceChange () {
            var selected = vm.vmRequest.genericVm;
            vm.vmRequest.hdd = selected.hdd;
            vm.vmRequest.processor = selected.processor;
            vm.vmRequest.ram = selected.ram;
        }

    }
})();
