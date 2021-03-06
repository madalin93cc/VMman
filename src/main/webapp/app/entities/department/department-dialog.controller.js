(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('DepartmentDialogController', DepartmentDialogController);

    DepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Department', 'User', '$http'];

    function DepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Department, User, $http) {
        var vm = this;

        vm.department = entity;
        vm.clear = clear;
        vm.save = save;
        vm.Users = User.query();
        vm.managers = [];
        $http.get('api/managers').then(function (response) {
            vm.managers = response.data
        });
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.department.id !== null) {
                Department.update(vm.department, onSaveSuccess, onSaveError);
            } else {
                Department.save(vm.department, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:departmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
