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