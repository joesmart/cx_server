/**
 * 
 */

//http response variable
var ERROR_SUCCESS = 'ERROR_SUCCESS';
var ERROR_NO_NETWORK = 'ERROR_NO_NETWORK';
var ERROR_TIMEOUT = 'ERROR_TIMEOUT';
var ERROR_UNAUTHORIZED = 'ERROR_UNAUTHORIZED';
var ERROR_BAD_REQUEST = 'ERROR_BAD_REQUEST';
var ERROR_FORBIDDEN = 'ERROR_FORBIDDEN';
var ERROR_NOT_FOUND = 'ERROR_NOT_FOUND';
var ERROR_CONFLICT = 'ERROR_CONFLICT';
var ERROR_INTERNAL = 'ERROR_INTERNAL';
var ERROR_INVALID_INPUT = 'ERROR_INVALID_INPUT';
var ERROR_MALFORMED_JSON = 'ERROR_MALFORMED_JSON';
var ERROR_UNKNOWN = 'ERROR_UNKNOWN';
var ERROR_USER_SET_OFFINE = 'ERROR_USER_SET_OFFINE';

//url
var MENU_MAIN_URL = "http://localhost:8080/mini-web/menu/category";
var METHOD_GET = "GET";
var METHOD_POST = "POST"
var METHOD_PUT = "PUT"
var METHOD_DELETE = "DELETE"

function Command(url, method, handler) {
	this.url = url;
	this.method = method;
	this.handler = handler;
	this.headers = {};
	this.headers['Accept'] = 'application/json';
	this.headers['Content-Type'] = 'application/json;charset=UTF-8';
}

Command.prototype.addHeader = function(name, value) {
	this.headers[name] = value;
};

Command.prototype.setPostbody = function(postbody) {
	this['postbody'] = postbody;
};

Command.prototype.onFailure = function(status, data) {
	if (status == 401) {
		this.challenge(data);
	} else {
		this.callback(getErrorCode(status), data);
	}
};

Command.prototype.onSuccess = function(status, data) {
	this.callback(ERROR_SUCCESS, data);
};

Command.prototype.onTimeout = function(status, data) {
	this.callback(ERROR_TIMEOUT, data);
};

Command.prototype.callback = function(code, data) {
	if (data == null || data == "") {
		this.callHandler(code, null);
	} else {
		var responseObj = null;
		try {
			responseObj = JSON.parse(data);
		} catch (error) {
			this.callHandler(code, null);
			return;
		}

		this.callHandler(code, responseObj);
	}
};

Command.prototype.callHandler = function(code, responseObj) {
	try {
		if (this.handler) {
			this.handler(code, responseObj);
		}
	} catch (e) {
		alert("e = " + e)
	}
};


function getErrorCode(status) {
		switch (status) {
		case 0:
			error = ERROR_NO_NETWORK;
			break;
		case 401:
			error = ERROR_UNAUTHORIZED;
			break;
		case 400:
			error = ERROR_BAD_REQUEST;
			break;
		case 403:
			error = ERROR_FORBIDDEN;
			break;
		case 404:
			error = ERROR_NOT_FOUND;
			break;
		case 409:
			error = ERROR_CONFLICT;
			break;
		case 500:
			error = parseHttpInternalError();
			break;
		default:
			error = ERROR_UNKNOWN;
			break;
		}
}
////////////////////////////////////////////////////////////////////////
function loginRequest(url, method, handler) {
	var command = new Command(url, method, handler);
	send(command);
}

//function sendRequest(command) {
//	send(command);
//}

function sendRequest(command, handler) {
	if(!(handler == 'undefined' || handler == null)) {
		command.handler = handler;
	}
	send(command);
}

function send(command) {
	$.ajax({
		type: command.method,
		url: command.url,
		data: command.postbody,
		dataType: 'text',
		beforeSend: function(XMLHttpRequest){
		},
		
		success: function(data, textStatus, jqXHR){
			var status = jqXHR.status;
			if(command != null) {
				command.onSuccess(status, data);
			}
		},
		complete: function() {
			
		},
		error: function(jqXHR, textStatus){
			var status = jqXHR.status;
			alert("status = " + status);
			if(command != null) {
				command.onFailure(status, null);
			}
			//请求出错处理
		},
		timeout: function() {
			var status = jqXHR.status;
			if(command != null) {
				command.onTimeout(status, null);
			}
		}
	});
} 