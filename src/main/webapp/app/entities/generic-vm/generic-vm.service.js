(function() {
    'use strict';
    angular
        .module('vMmanApp')
        .factory('GenericVm', GenericVm);

    GenericVm.$inject = ['$resource'];

    function GenericVm ($resource) {
        var resourceUrl =  'api/generic-vms/:id';

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
