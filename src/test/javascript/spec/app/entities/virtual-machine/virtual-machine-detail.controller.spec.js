'use strict';

describe('Controller Tests', function() {

    describe('VirtualMachine Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVirtualMachine, MockProject, MockOperatingSystem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVirtualMachine = jasmine.createSpy('MockVirtualMachine');
            MockProject = jasmine.createSpy('MockProject');
            MockOperatingSystem = jasmine.createSpy('MockOperatingSystem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VirtualMachine': MockVirtualMachine,
                'Project': MockProject,
                'OperatingSystem': MockOperatingSystem
            };
            createController = function() {
                $injector.get('$controller')("VirtualMachineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vMmanApp:virtualMachineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
