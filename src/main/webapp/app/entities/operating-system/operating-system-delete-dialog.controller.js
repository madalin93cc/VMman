(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('OperatingSystemDeleteController',OperatingSystemDeleteController);

    OperatingSystemDeleteController.$inject = ['$uibModalInstance', 'entity', 'OperatingSystem'];

    function OperatingSystemDeleteController($uibModalInstance, entity, OperatingSystem) {
        var vm = this;

        vm.operatingSystem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OperatingSystem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
