(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('GenericVmDetailController', GenericVmDetailController);

    GenericVmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GenericVm', 'VirtualMachine', 'VmRequest'];

    function GenericVmDetailController($scope, $rootScope, $stateParams, previousState, entity, GenericVm, VirtualMachine, VmRequest) {
        var vm = this;

        vm.genericVm = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vMmanApp:genericVmUpdate', function(event, result) {
            vm.genericVm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
