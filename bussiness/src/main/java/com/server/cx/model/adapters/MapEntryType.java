package com.server.cx.model.adapters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class MapEntryType<K, V> {

    private K key;
    private V value;

    public MapEntryType() {
    }

    public MapEntryType(Map.Entry<K, V> e) {
        key = e.getKey();
        value = e.getValue();
    }

    @XmlElement
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @XmlElement
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}