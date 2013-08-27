/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
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
