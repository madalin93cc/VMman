(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('OperatingSystemDetailController', OperatingSystemDetailController);

    OperatingSystemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OperatingSystem'];

    function OperatingSystemDetailController($scope, $rootScope, $stateParams, previousState, entity, OperatingSystem) {
        var vm = this;

        vm.operatingSystem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vMmanApp:operatingSystemUpdate', function(event, result) {
            vm.operatingSystem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
