(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VirtualMachineDetailController', VirtualMachineDetailController);

    VirtualMachineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VirtualMachine', 'Project', 'OperatingSystem', 'account'];

    function VirtualMachineDetailController($scope, $rootScope, $stateParams, previousState, entity, VirtualMachine, Project, OperatingSystem, account) {
        var vm = this;

        vm.virtualMachine = entity;
        vm.previousState = previousState.name;
        vm.account = account;
        vm.hasRole = hasRole;

        function hasRole(role) {
            return vm.account.authorities.indexOf(role) !== -1;
        }

        var unsubscribe = $rootScope.$on('vMmanApp:virtualMachineUpdate', function(event, result) {
            vm.virtualMachine = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
