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

import org.junit.Before;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import static org.fest.assertions.Assertions.assertThat;

public class SpringCacheCacheStrategyTest {

    private Cache cache;

    private SpringCacheCacheStrategy<String, Object> subject;

    @Before
    public void setUp() throws Exception {
        cache = new ConcurrentMapCache("test");
        subject = new SpringCacheCacheStrategy<>(cache);
    }

    @Test
    public void testClear() throws Exception {
        cache.put("key", "value");

        subject.clear();

        final ValueWrapper result = cache.get("key");
        assertThat(result).isNull();
    }

    @Test
    public void testDestroy() throws Exception {
        subject.destroy();

        final ValueWrapper result = cache.get("key");
        assertThat(result).isNull();
    }

    @Test
    public void testGetWithEntry() throws Exception {
        subject.put("key", "value");

        final Object result = subject.get("key");

        assertThat(result).isEqualTo("value");
    }

    @Test
    public void testGetWithNull() throws Exception {
        final Object result = subject.get("key");

        assertThat(result).isNull();
    }

    @Test
    public void testPut() throws Exception {
        subject.put("key", "value");

        final Object result = subject.get("key");

        assertThat(result).isSameAs("value");
    }

}
