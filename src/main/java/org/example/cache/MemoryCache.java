package org.example.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MemeryCache
 * @Author lsh
 * @Date 2023/7/17 15:17
 */
@Component
public class MemoryCache {

    private final ConcurrentHashMap<String, Object> cache;

    public MemoryCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object remove(String key) {
        return cache.remove(key);
    }
}
