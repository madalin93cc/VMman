(function() {
    'use strict';

    angular
        .module('vMmanApp')
        .factory('ProfileService', ProfileService);

    ProfileService.$inject = ['$http'];

    function ProfileService($http) {

        var dataPromise;

        var service = {
            getProfileInfo : getProfileInfo
        };

        return service;

        function getProfileInfo() {
            if (angular.isUndefined(dataPromise)) {
                dataPromise = $http.get('api/profile-info').then(function(result) {
                    if (result.data.activeProfiles) {
                        var response = {};
                        response.activeProfiles = result.data.activeProfiles;
                        response.inProduction = result.data.activeProfiles.indexOf("prod") !== -1;
                        return response;
                    }
                });
            }
            return dataPromise;
        }
    }
})();
