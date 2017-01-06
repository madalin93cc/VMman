(function() {
    'use strict';
    angular
        .module('vMmanApp')
        .factory('OperatingSystem', OperatingSystem);

    OperatingSystem.$inject = ['$resource'];

    function OperatingSystem ($resource) {
        var resourceUrl =  'api/operating-systems/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
