(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VirtualMachineDetailController', VirtualMachineDetailController);

    VirtualMachineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VirtualMachine', 'Project', 'OperatingSystem'];

    function VirtualMachineDetailController($scope, $rootScope, $stateParams, previousState, entity, VirtualMachine, Project, OperatingSystem) {
        var vm = this;

        vm.virtualMachine = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vMmanApp:virtualMachineUpdate', function(event, result) {
            vm.virtualMachine = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
