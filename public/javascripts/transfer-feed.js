var transferFeed = angular.module('transferFeed', ['ngRoute']);

transferFeed.config(['$routeProvider', function($routeProvider) {

	$routeProvider
	.when(
		'/',
		{
			controller: 'SearchController',
			templateUrl: '/templates/search.html'
		}
	)
	.otherwise({
		redirectTo: '/'
	})
}]);

// will handle logic for searching for transfer new
transferFeed.controller('SearchController', ['$scope', function(scope) {

	scope.team = 'liverpool'
	
	scope.subscribe = function() {
		console.log(scope.team);
	}

}]);