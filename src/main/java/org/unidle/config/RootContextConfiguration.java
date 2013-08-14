package org.unidle.config;

import com.google.common.base.Joiner;
import com.jolbox.bonecp.BoneCPDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.extensions.locator.WebjarUriLocator;
import ro.isdc.wro.extensions.processor.css.RubySassCssProcessor;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.locator.ClasspathUriLocator;
import ro.isdc.wro.model.resource.locator.ServletContextUriLocator;
import ro.isdc.wro.model.resource.locator.UrlUriLocator;
import ro.isdc.wro.model.resource.locator.factory.ConfigurableLocatorFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@ComponentScan("org.unidle.service")
@Configuration
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

    @Value("${unidle.hibernate.ehcache.configurationResourceName}")
    private String hibernateEhcacheConfigurationResourceName;

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

    @Value("${unidle.wro.cacheGzippedContent}")
    private boolean wroCacheGzippedContent;

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

    @Value("${unidle.wro.resourceWatcherUpdatePeriod}")
    private long wroResourceWatcherUpdatePeriod;

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
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        final Map<String, Object> jpaProperties = new LinkedHashMap<String, Object>();
        jpaProperties.put("net.sf.ehcache.configurationResourceName", hibernateEhcacheConfigurationResourceName);
        jpaProperties.put("hibernate.cache.region.factory_class", hibernateEhcacheRegionFactoryClass);
        jpaProperties.put("hibernate.cache.use_query_cache", hibernateUseQueryCache);
        jpaProperties.put("hibernate.cache.use_second_level_cache", hibernateUseSecondLevelCache);
        jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);

        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(jpaVendorDatabase);

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.unidle.domain");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(dataSourceDriverClass);
        dataSource.setJdbcUrl(dataSourceUrl);
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
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages/unidle");

        return messageSource;
    }

    @Bean
    public SpringLiquibase springLiquibase() {
        final SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource());
        springLiquibase.setDefaultSchema("public");
        springLiquibase.setChangeLog("classpath:liquibase/changelog.xml");

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

        return wroFilter;
    }

    @Bean
    public WroManagerFactory wroManagerFactory() {
        final ConfigurableWroManagerFactory wroManagerFactory = new ConfigurableWroManagerFactory();

        final Properties properties = new Properties();

        properties.put(ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS, RubySassCssProcessor.ALIAS);
        properties.put(ConfigurableLocatorFactory.PARAM_URI_LOCATORS, Joiner.on(',').join(WebjarUriLocator.ALIAS,
                                                                                          ServletContextUriLocator.ALIAS,
                                                                                          UrlUriLocator.ALIAS,
                                                                                          ClasspathUriLocator.ALIAS));

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

        wroManagerFactory.setConfigProperties(properties);

        return wroManagerFactory;
    }

}
