var transferStream = angular.module('transferStream', []);

// websocket url for play frontend api
transferStream.value('url', 'ws://127.0.0.1:9000/feed');

// handles setting-up/tearing-down/sending-msgs of websocket channel
transferStream.factory('eventStream', ['url', function(url) {

	var ws = new WebSocket(url);

	ws.onopen = function(evnt) { console.log('channel open: ', evnt); };
	ws.onclose = function(evnt) { console.log('channel closed: ', evnt); };

	return function(msg) { ws.send(msg); }
}]);