(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'VirtualMachine', 'Department'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, VirtualMachine, Department) {
        var vm = this;

        vm.project = entity;
        vm.departments = Department.query();
        vm.clear = clear;
        vm.save = save;
        vm.virtualmachines = VirtualMachine.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
