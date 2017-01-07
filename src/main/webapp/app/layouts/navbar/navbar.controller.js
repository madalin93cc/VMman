(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.sidebarUrl = "app/layouts/navbar/sidebar.html";

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        vm.selectedMenu = 'dashboard';
        vm.collapseVar = 0;
        vm.multiCollapseVar = 0;

        vm.check = check;
        vm.multiCheck = multiCheck;
        function check(x){

            if(x==vm.collapseVar)
                vm.collapseVar = 0;
            else
                vm.collapseVar = x;
        }

        function multiCheck(y){

            if(y==vm.multiCollapseVar)
                vm.multiCollapseVar = 0;
            else
                vm.multiCollapseVar = y;
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

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
