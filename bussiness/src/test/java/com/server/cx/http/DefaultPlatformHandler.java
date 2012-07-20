package com.server.cx.http;

public class DefaultPlatformHandler implements PlatformHandler {

	@Override
	public void handleResponseDefault(int code, Object obj) {
		System.out.println("handle default");
	}

	@Override
	public void handleResponse(String code, Object obj) {
		System.out.println("handle response");
	}

}
