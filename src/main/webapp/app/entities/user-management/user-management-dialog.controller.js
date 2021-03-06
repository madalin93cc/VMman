(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'Department'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, Department) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'];
        vm.departments = Department.query();
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (!angular.isArray(vm.user.authorities)) {
                vm.user.authorities = [vm.user.authorities];
            }
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
