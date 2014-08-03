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

// websocket url for play frontend api
transferFeed.value('url', 'ws://127.0.0.1:9000/feed');

// subscribing teams to live transfer updates
transferFeed.controller('TransferController', ['$scope', 'url', 'eventStream', 
	function($scope, url, stream) {

	$scope.team = 'liverpool';

	$scope.ws = new stream(url);
	
	// begins websocket connection with play/akka frontends
	$scope.subscribe = function() {
		//$scope.ws.send($scope.team);
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
			'event': 'add-source',
			'name': $scope.source.name,
			'url': $scope.source.url
		};

		$scope.$parent.ws.send(msg);
	}
}]);