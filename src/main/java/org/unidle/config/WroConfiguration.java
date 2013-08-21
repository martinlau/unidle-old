package org.unidle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.unidle.cache.SpringCacheCacheStrategy;
import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.cache.CacheValue;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.locator.factory.ConfigurableLocatorFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;

import java.util.Properties;

@Configuration
@PropertySource("classpath:unidle.properties")
public class WroConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private CacheManager cacheManager;

    @Value("${unidle.wro.cacheGzippedContent}")
    private boolean wroCacheGzippedContent;

    @Value("${unidle.wro.cacheName}")
    private String wroCacheName;

    @Value("${unidle.wro.cacheUpdatePeriod}")
    private Long wroCacheUpdatePeriod;

    @Value("${unidle.wro.debug}")
    private boolean wroDebug;

    @Value("${unidle.wro.disableCache}")
    private boolean wroDisableCache;

    @Value("${unidle.wro.encoding}")
    private String wroEncoding;

    @Value("${unidle.wro.gzipResources}")
    private boolean wroGzipResources;

    @Value("${unidle.wro.ignoreEmptyGroup}")
    private boolean wroIgnoreEmptyGroup;

    @Value("${unidle.wro.ignoreFailingProcessor}")
    private boolean wroIgnoreFailingProcessor;

    @Value("${unidle.wro.ignoreMissingResources}")
    private boolean wroIgnoreMissingResources;

    @Value("${unidle.wro.jmxEnabled}")
    private boolean wroJmxEnabled;

    @Value("${unidle.wro.modelUpdatePeriod}")
    private long wroModelUpdatePeriod;

    @Value("${unidle.wro.parallelPreprocessing}")
    private boolean wroParallelPreprocessing;

    @Value("${unidle.wro.postProcessors}")
    private String wroPostProcessors;

    @Value("${unidle.wro.resourceWatcherUpdatePeriod}")
    private long wroResourceWatcherUpdatePeriod;

    @Value("${unidle.wro.uriLocators}")
    private String wroUriLocators;

    @Bean
    public WroFilter wroFilter() {
        final ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();

        wroFilter.setWroManagerFactory(wroManagerFactory());
        wroFilter.setProperties(wroProperties());

        return wroFilter;
    }

    @Bean
    public WroManagerFactory wroManagerFactory() {

        final Properties properties = wroProperties();

        final ConfigurableWroManagerFactory wroManagerFactory = new ConfigurableWroManagerFactory();

        wroManagerFactory.setCacheStrategy(cacheStrategy());
        wroManagerFactory.setConfigProperties(properties);

        return wroManagerFactory;
    }

    @Bean
    public SpringCacheCacheStrategy<CacheKey, CacheValue> cacheStrategy() {
        final Cache cache = cacheManager.getCache(wroCacheName);

        return new SpringCacheCacheStrategy<>(cache);
    }

    @Bean
    public Properties wroProperties() {
        final Properties properties = new Properties();

        properties.put(ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS, wroPostProcessors);
        properties.put(ConfigurableLocatorFactory.PARAM_URI_LOCATORS, wroUriLocators);

        properties.put(ConfigConstants.cacheGzippedContent, wroCacheGzippedContent);
        properties.put(ConfigConstants.cacheUpdatePeriod.name(), wroCacheUpdatePeriod);
        properties.put(ConfigConstants.debug.name(), wroDebug);
        properties.put(ConfigConstants.disableCache.name(), wroDisableCache);
        properties.put(ConfigConstants.encoding.name(), wroEncoding);
        properties.put(ConfigConstants.gzipResources.name(), wroGzipResources);
        properties.put(ConfigConstants.ignoreEmptyGroup.name(), wroIgnoreEmptyGroup);
        properties.put(ConfigConstants.ignoreFailingProcessor.name(), wroIgnoreFailingProcessor);
        properties.put(ConfigConstants.ignoreMissingResources.name(), wroIgnoreMissingResources);
        properties.put(ConfigConstants.jmxEnabled.name(), wroJmxEnabled);
        properties.put(ConfigConstants.modelUpdatePeriod.name(), wroModelUpdatePeriod);
        properties.put(ConfigConstants.parallelPreprocessing.name(), wroParallelPreprocessing);
        properties.put(ConfigConstants.resourceWatcherUpdatePeriod.name(), wroResourceWatcherUpdatePeriod);

        return properties;
    }

}
