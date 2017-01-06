(function() {
    'use strict';
    angular
        .module('vMmanApp')
        .factory('VirtualMachine', VirtualMachine);

    VirtualMachine.$inject = ['$resource'];

    function VirtualMachine ($resource) {
        var resourceUrl =  'api/virtual-machines/:id';

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
