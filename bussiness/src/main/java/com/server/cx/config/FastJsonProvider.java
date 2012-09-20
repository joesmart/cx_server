package com.server.cx.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.jaxrs.json.util.ClassKey;
import com.google.common.base.Stopwatch;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.OutputSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: yanjianzou
 * Date: 9/12/12
 * Time: 3:55 PM
 * FileName:FastJsonProvider
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FastJsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

    private static Logger logger = LoggerFactory.getLogger(FastJsonProvider.class);
    /**
     * Looks like we need to worry about accidental
     * data binding for types we shouldn't be handling. This is
     * probably not a very good way to do it, but let's start by
     * blacklisting things we are not to handle.
     * <p/>
     * (why ClassKey? since plain old Class has no hashCode() defined,
     * lookups are painfully slow)
     */
    public final static HashSet<ClassKey> _untouchables = new HashSet<ClassKey>();

    static {
        // First, I/O things (direct matches)
        _untouchables.add(new ClassKey(java.io.InputStream.class));
        _untouchables.add(new ClassKey(java.io.Reader.class));
        _untouchables.add(new ClassKey(java.io.OutputStream.class));
        _untouchables.add(new ClassKey(java.io.Writer.class));

        // then some primitive types
        _untouchables.add(new ClassKey(char[].class));

        /* 28-Jan-2012, tatu: 1.x excluded some additional types;
         *   but let's relax these a bit:
         */
        /* 27-Apr-2012, tatu: Ugh. As per
         *   [https://github.com/FasterXML/jackson-jaxrs-json-provider/issues/12]
         *  better revert this back, to make them untouchable again.
         */
        _untouchables.add(new ClassKey(String.class));
        _untouchables.add(new ClassKey(byte[].class));
    }


    /**
     * These are classes that we never use for reading
     * (never try to deserialize instances of these types).
     */
    public final static Class<?>[] _unreadableClasses = new Class<?>[]{
            InputStream.class, Reader.class
    };

    /**
     * These are classes that we never use for writing
     * (never try to serialize instances of these types).
     */
    public final static Class<?>[] _unwritableClasses = new Class<?>[]{
            OutputStream.class, Writer.class,
            StreamingOutput.class, Response.class
    };


    /**
     * Set of types (classes) that provider should ignore for data binding
     */
    protected HashSet<ClassKey> _cfgCustomUntouchables;

    /**
     * JSONP function name to use for automatic JSONP wrapping, if any;
     * if null, no JSONP wrapping is done.
     * Note that this is the default value that can be overridden on
     * per-endpoint basis.
     */
    protected String _jsonpFunctionName;

    /*
   /**********************************************************
   /* Context configuration
   /**********************************************************
    */

    /**
     * Injectable context object used to locate configured
     * instance of {@link com.fasterxml.jackson.databind.ObjectMapper} to use for actual
     * serialization.
     */
    @Context
    protected Providers _providers;

    /*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */

    /**
     * Whether we want to actually check that Jackson has
     * a serializer for given type. Since this should generally
     * be the case (due to auto-discovery) and since the call
     * to check availability can be bit expensive, defaults to false.
     */
    protected boolean _cfgCheckCanSerialize = false;

    /**
     * Whether we want to actually check that Jackson has
     * a deserializer for given type. Since this should generally
     * be the case (due to auto-discovery) and since the call
     * to check availability can be bit expensive, defaults to false.
     */
    protected boolean _cfgCheckCanDeserialize = false;

    /*
    /**********************************************************
    /* Construction
    /**********************************************************
     */

    public FastJsonProvider() {

    }

    protected boolean isJsonType(MediaType mediaType) {
        /* As suggested by Stephen D, there are 2 ways to check: either
         * being as inclusive as possible (if subtype is "json"), or
         * exclusive (major type "application", minor type "json").
         * Let's start with inclusive one, hard to know which major
         * types we should cover aside from "application".
         */
        if (mediaType != null) {
            // Ok: there are also "xxx+json" subtypes, which count as well
            String subtype = mediaType.getSubtype();
            return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json");
        }
        /* Not sure if this can happen; but it seems reasonable
         * that we can at least produce json without media type?
         */
        return true;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!isJsonType(mediaType)) {
            return false;
        }
        /* Ok: looks like we must weed out some core types here; ones that
         * make no sense to try to bind from JSON:
         */
        if (_untouchables.contains(new ClassKey(type))) {
            return false;
        }
        // and there are some other abstract/interface types to exclude too:
        for (Class<?> cls : _unreadableClasses) {
            if (cls.isAssignableFrom(type)) {
                return false;
            }
        }
        // as well as possible custom exclusions
        if (_containedIn(type, _cfgCustomUntouchables)) {
            return false;
        }
        return true;
    }

    protected static boolean _containedIn(Class<?> mainType, HashSet<ClassKey> set) {
        if (set != null) {
            ClassKey key = new ClassKey(mainType);
            // First: type itself?
            if (set.contains(key)) return true;
            // Then supertypes (note: will not contain Object.class)
            for (Class<?> cls : findSuperTypes(mainType, null)) {
                key.reset(cls);
                if (set.contains(key)) return true;
            }
        }
        return false;
    }


    private static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore) {
        return findSuperTypes(cls, endBefore, new ArrayList<Class<?>>(8));
    }

    private static List<Class<?>> findSuperTypes(Class<?> cls, Class<?> endBefore, List<Class<?>> result) {
        _addSuperTypes(cls, endBefore, result, false);
        return result;
    }

    private static void _addSuperTypes(Class<?> cls, Class<?> endBefore, Collection<Class<?>> result, boolean addClassItself) {
        if (cls == endBefore || cls == null || cls == Object.class) {
            return;
        }
        if (addClassItself) {
            if (result.contains(cls)) { // already added, no need to check supers
                return;
            }
            result.add(cls);
        }
        for (Class<?> intCls : cls.getInterfaces()) {
            _addSuperTypes(intCls, endBefore, result, true);
        }
        _addSuperTypes(cls.getSuperclass(), endBefore, result, true);
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
//        StringBuffer stringBuffer = Files

        List<String> strings = CharStreams.readLines(new BufferedReader(new InputStreamReader(entityStream)));
        StringBuffer stringBuffer =new StringBuffer();
        for(String temp:strings){
            stringBuffer.append(temp);
        }
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        Object result = JSON.parseObject(stringBuffer.toString(), genericType);
        stopwatch.stop();
        logger.info("FastJson deserialize time:"+stopwatch.elapsedTime(TimeUnit.MICROSECONDS));
        return result;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!isJsonType(mediaType)) {
            return false;
        }

        /* Ok: looks like we must weed out some core types here; ones that
         * make no sense to try to bind from JSON:
         */
        if (_untouchables.contains(new ClassKey(type))) {
            return false;
        }
        // but some are interface/abstract classes, so
        for (Class<?> cls : _unwritableClasses) {
            if (cls.isAssignableFrom(type)) {
                return false;
            }
        }
        // and finally, may have additional custom types to exclude
        if (_containedIn(type, _cfgCustomUntouchables)) {
            return false;
        }

        return true;
    }

    @Override
    public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        String jsonString = JSON.toJSONString(o,SerializerFeature.WriteClassName,SerializerFeature.PrettyFormat);

        httpHeaders.putSingle("Content-Type","application/json; charset=UTF-8");
        ByteStreams.write(jsonString.getBytes(),new OutputSupplier<OutputStream>() {
            @Override
            public OutputStream getOutput() throws IOException {
                return entityStream;
            }
        });

        stopwatch.stop();
        logger.info("Fast Json Convert time:"+stopwatch.elapsedTime(TimeUnit.MICROSECONDS));
    }

}
