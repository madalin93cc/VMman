(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'VirtualMachine'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, VirtualMachine) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vMmanApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
