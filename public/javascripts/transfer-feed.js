var transferFeed = angular.module('transferFeed', ['ngRoute', 'transferStream']);

transferFeed.config(['$routeProvider', function($routeProvider) {

	$routeProvider
	.when(
		'/', {
			controller: 'TransferController',
			templateUrl: '/templates/transfer.html'
		}
	)
	.otherwise({
		redirectTo: '/'
	})
}]);

// subscribing teams to live transfer updates
transferFeed.controller('TransferController', ['$scope', 'transferService', 
	function($scope, service) {

	$scope.team = 'liverpool';

	$scope.service = service

	// begins websocket connection with play/akka frontends
	$scope.subscribe = function() {
		//$scope.service.team($scope.team);
	};

}]);

// adds transfer source
// NOTE: major sources could be configured in akka backend
transferFeed.controller('SourcesController', ['$scope', function($scope) {

	$scope.adding = false;

	$scope.source = {};

	$scope.watchlist = function() {
		$scope.adding = true;
	}

	// adds a new media source to the watch list
	$scope.addSource = function() {
		$scope.adding = false;

		var msg = { 
			'name': $scope.source.name,
			'url': $scope.source.url
		};

		$scope.$parent.service.source(msg);
	}
}]);