(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'account'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, account) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.account = account;
        vm.hasRole = hasRole;

        function hasRole(role) {
            return vm.account.authorities.indexOf(role) !== -1;
        }

        var unsubscribe = $rootScope.$on('vMmanApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
