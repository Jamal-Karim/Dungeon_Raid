package com.jamal_karim.dungeon.utils;

import java.util.*;

public class TestVariables {
    private final Map<String, Object> map;

    public TestVariables() {
        this.map = new HashMap<>();
    }

    public void store(String key, Object value) {
        if(key != null){
            String extractedKey = extractKey(key);
            if (this.map.containsKey(extractedKey)) {
                throw new RuntimeException("TestVariables already contains key: " + extractedKey);
            }
            this.map.put(extractedKey, value);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (key == null) return null;
        return (T) this.map.get(extractKey(key));
    }

    private String extractKey(String key) {
        if (key.startsWith("{") && key.endsWith("}") && key.length() > 2) {
            return key.substring(1, key.length() - 1);
        }
        return key;
    }
}
