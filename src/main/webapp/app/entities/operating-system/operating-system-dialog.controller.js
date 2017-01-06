(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('OperatingSystemDialogController', OperatingSystemDialogController);

    OperatingSystemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OperatingSystem'];

    function OperatingSystemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OperatingSystem) {
        var vm = this;

        vm.operatingSystem = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.operatingSystem.id !== null) {
                OperatingSystem.update(vm.operatingSystem, onSaveSuccess, onSaveError);
            } else {
                OperatingSystem.save(vm.operatingSystem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vMmanApp:operatingSystemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
