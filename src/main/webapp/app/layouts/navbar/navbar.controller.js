(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', '$scope', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, $scope, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.sidebarUrl = "app/layouts/navbar/sidebar.html";

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.hasRole = hasRole;
        vm.$state = $state;


        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
            });
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('login');
        }

        function hasRole(role) {
            return vm.account.authorities.indexOf(role) !== -1;
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
