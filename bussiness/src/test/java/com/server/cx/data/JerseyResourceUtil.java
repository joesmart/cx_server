package com.server.cx.data;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;

public class JerseyResourceUtil {
	private static Client client;
	public static String BASE_URL = "http://10.90.3.99:8080/bussiness/pim/menu/1.0/";
	
	public static Client getClient() {
		if(client == null) {
			client = Client.create();
			client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		}
		return client;
 	}
}
