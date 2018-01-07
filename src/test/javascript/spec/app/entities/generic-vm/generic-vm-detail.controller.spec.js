'use strict';

describe('Controller Tests', function() {

    describe('GenericVm Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGenericVm, MockVirtualMachine, MockVmRequest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGenericVm = jasmine.createSpy('MockGenericVm');
            MockVirtualMachine = jasmine.createSpy('MockVirtualMachine');
            MockVmRequest = jasmine.createSpy('MockVmRequest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GenericVm': MockGenericVm,
                'VirtualMachine': MockVirtualMachine,
                'VmRequest': MockVmRequest
            };
            createController = function() {
                $injector.get('$controller')("GenericVmDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vMmanApp:genericVmUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
