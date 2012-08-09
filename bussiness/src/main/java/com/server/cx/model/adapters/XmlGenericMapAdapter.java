package com.server.cx.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class XmlGenericMapAdapter<K, V> extends XmlAdapter<MapType<K, V>, Map<K, V>> {

    @Override
    public Map<K, V> unmarshal(MapType<K, V> v) throws Exception {
        HashMap<K, V> map = new HashMap<K, V>();
        for (MapEntryType<K, V> mapEntryType : v.getEntry()) {
            map.put(mapEntryType.getKey(), mapEntryType.getValue());
        }
        return map;
    }

    @Override
    public MapType<K, V> marshal(Map<K, V> v) throws Exception {
        MapType<K, V> mapType = new MapType<K, V>();
        if (v != null) {
            for (Map.Entry<K, V> entry : v.entrySet()) {
                MapEntryType<K, V> mapEntryType = new MapEntryType<K, V>();
                mapEntryType.setKey(entry.getKey());
                mapEntryType.setValue(entry.getValue());
                mapType.getEntry().add(mapEntryType);
            }
            return mapType;
        } else {
            return null;
        }

    }

}
