package com.server.cx.util.business;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;

public class JerseyResourceUtil {
    private static Client client = null;

    public static Client getClient() {
        if (client == null) {
            client = Client.create();
            client.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
        }
        return client;
    }
}
