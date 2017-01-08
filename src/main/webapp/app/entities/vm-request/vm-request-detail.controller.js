(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('VmRequestDetailController', VmRequestDetailController);

    VmRequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VmRequest', 'OperatingSystem', 'User', 'account'];

    function VmRequestDetailController($scope, $rootScope, $stateParams, previousState, entity, VmRequest, OperatingSystem, User, account) {
        var vm = this;

        vm.vmRequest = entity;
        vm.previousState = previousState.name;
        vm.account = account;
        vm.hasRole = hasRole;

        function hasRole(role) {
            return vm.account.authorities.indexOf(role) !== -1;
        }

        var unsubscribe = $rootScope.$on('vMmanApp:vmRequestUpdate', function(event, result) {
            vm.vmRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
