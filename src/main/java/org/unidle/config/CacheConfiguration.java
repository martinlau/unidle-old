package org.unidle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableCaching
@PropertySource("classpath:unidle.properties")
public class CacheConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${unidle.ehcache.configurationResource}")
    private Resource ehcacheConfigurationResource;

    @Bean
    public CacheManager cacheManager() throws IOException {
        final InputStream inputStream = ehcacheConfigurationResource.getInputStream();
        final net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.create(inputStream);

        final EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();

        ehCacheCacheManager.setCacheManager(cacheManager);

        return ehCacheCacheManager;
    }

}
