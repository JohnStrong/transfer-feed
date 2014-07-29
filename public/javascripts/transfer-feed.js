var transferFeed = angular.module('transferFeed', ['ngRoute']);

transferFeed.config(['$routeProvider', function($routeProvider) {

	$routeProvider
	.when(
		'/',
		{
			controller: 'TransferController',
			templateUrl: '/templates/transfer.html'
		}
	)
	.otherwise({
		redirectTo: '/'
	})
}]);

// will handle logic for searching for transfer new
transferFeed.controller('TransferController', ['$scope', function(scope) {

	scope.team = 'liverpool';

	scope.watchlist = [];

	// begins websocket connection with play/akka frontends
	scope.subscribe = function() {
		// todo
	};

}]);

// add short term transfer source (major sources are configured in akka backend)
transferFeed.controller('SourcesController', ['$scope', function(scope) {

	scope.adding = false;

	scope.source = {};

	scope.watchlist = function() {
		scope.adding = true;
	}

	scope.addSource = function() {
		scope.adding = false;
		scope.$parent.watchlist.push(scope.source);
	}
}]);