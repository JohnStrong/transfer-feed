var transferStream = angular.module('transferStream', []);

// handles setting-up/tearing-down/sending-msgs of websocket channel
transferStream.service('eventStream', function() {

	function EventSocket(url) {
		this.ws = new WebSocket(url);
		this.ws.onopen = this.onOpen;
		this.ws.onclose = this.onClose;
	}

	// sends a message to source
	EventSocket.prototype.send = function(msg) {
		this.ws.send(msg);
	};

	/**
	 * 
	 * logging type methods triggered when a socket is opened, closed
	 **/

	EventSocket.prototype.onOpen = function(evnt) { 
		console.log('channel open: ', evnt); 
	}

	EventSocket.prototype.onClose = function(evnt) { 
		console.log('channel closed: ', evnt); 
	};

	return EventSocket;
});

// websocket url for play frontend api
transferStream.value('url', 'ws://127.0.0.1:9000/feed');

transferStream.factory('transferService', ['eventStream', 'url', function(Stream, url) {

	var _stream = new Stream(url),

	_toStream = function(json) {
		_stream.send(JSON.stringify(json))
	};

	return {

		'source': function(body) {
			body['event'] = 'add-source';
			_toStream(body);
		},

		'team': function(body) {
			body['event'] = 'add-team';
			_toStream(body);
		}
	}
}]);