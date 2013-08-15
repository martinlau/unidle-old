package org.unidle.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@ComponentScan("org.unidle.service")
@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages = "org.unidle.repository")
@EnableTransactionManagement
@PropertySource("classpath:unidle.properties")
public class RootContextConfiguration {

    @Value("${unidle.dataSource.driverClass}")
    private String dataSourceDriverClass;

    @Value("${unidle.dataSource.password}")
    private String dataSourcePassword;

    @Value("${unidle.dataSource.url}")
    private String dataSourceUrl;

    @Value("${unidle.dataSource.username}")
    private String dataSourceUsername;

    @Value("${unidle.ehcache.configurationResourceName}")
    private String ehcacheConfigurationResourceName;

    @Value("${unidle.hibernate.ehcache.regionFactoryClass}")
    private String hibernateEhcacheRegionFactoryClass;

    @Value("${unidle.hibernate.hbm2ddl}")
    private String hibernateHbm2ddl;

    @Value("${unidle.hibernate.useQueryCache}")
    private String hibernateUseQueryCache;

    @Value("${unidle.hibernate.useSecondLevelCache}")
    private String hibernateUseSecondLevelCache;

    @Value("${unidle.jpaVendor.database}")
    private Database jpaVendorDatabase;

    @Value("${unidle.liquibase.changelog}")
    private String liquibaseChangelog;

    @Value("${unidle.messageSource.baseName}")
    private String messageSourceBaseName;

    @Value("${unidle.messageSource.cacheSeconds}")
    private int messageSourceCacheSeconds;

    @Value("${unidle.messageSource.encoding}")
    private String messageSourceEncoding;

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
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public FactoryBean<EntityManager> entityManager() {
        final EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();

        final SharedEntityManagerBean entityManagerBean = new SharedEntityManagerBean();

        entityManagerBean.setEntityManagerFactory(entityManagerFactory);

        return entityManagerBean;
    }

    @Bean
    @DependsOn("springLiquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabase(jpaVendorDatabase);

        final Map<String, Object> jpaProperties = new LinkedHashMap<String, Object>();

        jpaProperties.put("net.sf.ehcache.configurationResourceName", ehcacheConfigurationResourceName);
        jpaProperties.put("hibernate.cache.region.factory_class", hibernateEhcacheRegionFactoryClass);
        jpaProperties.put("hibernate.cache.use_query_cache", hibernateUseQueryCache);
        jpaProperties.put("hibernate.cache.use_second_level_cache", hibernateUseSecondLevelCache);
        jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.unidle.domain");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(dataSourceDriverClass);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);

        return dataSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding(messageSourceEncoding);
        messageSource.setBasename(messageSourceBaseName);
        messageSource.setCacheSeconds(messageSourceCacheSeconds);

        return messageSource;
    }

    @Bean
    public SpringLiquibase springLiquibase() {

        @SuppressWarnings("unchecked")
        final Class<Driver> driverClass = (Class<Driver>) ClassUtils.resolveClassName(dataSourceDriverClass, ClassUtils.getDefaultClassLoader());

        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(driverClass);
        dataSource.setPassword(dataSourcePassword);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);

        final SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(liquibaseChangelog);

        return springLiquibase;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setDataSource(dataSource());
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

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
        final Cache cache = cacheManager().getCache(wroCacheName);

        return new SpringCacheCacheStrategy<>(cache);
    }

    @Bean
    public CacheManager cacheManager() {
        final net.sf.ehcache.CacheManager ehCacheManager = net.sf.ehcache.CacheManager.create(ehcacheConfigurationResourceName);

        final EhCacheCacheManager cacheManager = new EhCacheCacheManager();

        cacheManager.setCacheManager(ehCacheManager);

        return cacheManager;
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
