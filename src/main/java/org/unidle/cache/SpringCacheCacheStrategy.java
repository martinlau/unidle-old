package org.unidle.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import ro.isdc.wro.cache.CacheStrategy;

public class SpringCacheCacheStrategy<K, V> implements CacheStrategy<K, V> {

    private final Cache cache;

    public SpringCacheCacheStrategy(final Cache cache) {
        this.cache = cache;
    }

    @Override
    public void put(final K key,
                    final V value) {
        cache.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(final K key) {
        final ValueWrapper valueWrapper = cache.get(key);
        return valueWrapper != null
               ? (V) valueWrapper.get()
               : null;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void destroy() {
        cache.clear();
    }

}
