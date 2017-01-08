(function() {
    'use strict';
    angular
        .module('vMmanApp')
        .factory('VmRequest', VmRequest);

    VmRequest.$inject = ['$resource'];

    function VmRequest ($resource) {
        var resourceUrl =  'api/vm-requests/:id';

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
