// main transfer-feed spec
describe('TransferController', function() {

	var scope, controller;

	beforeEach(angular.mock.module('transferFeed'));

	beforeEach(angular.mock.inject(function($rootScope, $controller) {
		scope = $rootScope.$new();
		controller = $controller('TransferController', { $scope: scope });
	}));

	it('should contain a default team selection in "liverpool"', function() {
		expect(scope.team).toBe('liverpool');
	});

});