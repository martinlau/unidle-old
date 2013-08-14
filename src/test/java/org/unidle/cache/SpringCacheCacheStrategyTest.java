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
