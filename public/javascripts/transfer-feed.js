var transferFeed = angular.module('transferFeed', ['ngRoute']);

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

// handles setting-up/tearing-down/sending-msgs of websocket channel
transferFeed.factory('wsStream', function() {

	var url = 'ws://127.0.0.1:9000/feed',
	ws = new WebSocket(url);

	ws.onopen = function(evnt) { console.log('channel open: ', evnt); };
	ws.onclose = function(evnt) { console.log('channel closed: ', evnt); };

	return {
		'send': function(msg) { ws.send(msg); }
	};
});

// subscribing teams to live transfer updates
transferFeed.controller('TransferController', ['$scope', 'wsStream', function($scope, wsStream) {

	$scope.team = 'liverpool';

	$scope.ws = wsStream;
	
	// begins websocket connection with play/akka frontends
	$scope.subscribe = function() {
		// todo
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

	$scope.addSource = function() {
		$scope.adding = false;
	}
}]);