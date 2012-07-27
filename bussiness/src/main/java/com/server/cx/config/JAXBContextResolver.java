package com.server.cx.config;

import com.server.cx.dto.CXInfo;
import com.server.cx.dto.Result;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.dto.adapters.MapEntryType;
import com.server.cx.dto.adapters.MapType;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;

    private final Set<Class> types;

    private final Class[] cTypes = {CXInfo.class, Result.class, UserCXInfo.class, MapEntryType.class, MapType.class};

    public JAXBContextResolver() throws Exception {
        this.types = new HashSet(Arrays.asList(cTypes));
        this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), cTypes);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        return (types.contains(objectType)) ? context : null;
    }

}
