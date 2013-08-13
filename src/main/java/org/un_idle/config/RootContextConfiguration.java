package org.un_idle.config;

import com.google.common.base.Joiner;
import com.jolbox.bonecp.BoneCPDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
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
import ro.isdc.wro.model.resource.support.hash.ConfigurableHashStrategy;
import ro.isdc.wro.model.resource.support.hash.SHA1HashStrategy;
import ro.isdc.wro.model.resource.support.naming.ConfigurableNamingStrategy;
import ro.isdc.wro.model.resource.support.naming.TimestampNamingStrategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;

@ComponentScan("org.un_idle.service")
@Configuration
@EnableJpaRepositories(basePackages = "org.un_idle.repository")
@EnableTransactionManagement
@PropertySource("classpath:un-idle.properties")
public class RootContextConfiguration {

    @Value("${un-idle.wro.cacheUpdatePeriod}")
    private Long cacheUpdatePeriod;

    @Autowired
    private DataSource dataSource;

    @Value("${un-idle.hibernate.ehcache.configurationResourceName}")
    private String hibernateEhcacheConfigurationResourceName;

    @Value("${un-idle.hibernate.ehcache.regionFactoryClass}")
    private String hibernateEhcacheRegionFactoryClass;

    @Value("${un-idle.hibernate.hbm2ddl}")
    private String hibernateHbm2ddl;

    @Value("${un-idle.hibernate.useQueryCache}")
    private String hibernateUseQueryCache;

    @Value("${un-idle.hibernate.useSecondLevelCache}")
    private String hibernateUseSecondLevelCache;

    @Value("${un-idle.jpaVendor.database}")
    private Database jpaVendorDatabase;

    @Value("${un-idle.wro.cacheGzippedContent}")
    private boolean wroCacheGzippedContent;

    @Value("${un-idle.wro.debug}")
    private boolean wroDebug;

    @Value("${un-idle.wro.disableCache}")
    private boolean wroDisableCache;

    @Value("${un-idle.wro.encoding}")
    private String wroEncoding;

    @Value("${un-idle.wro.gzipResources}")
    private boolean wroGzipResources;

    @Value("${un-idle.wro.ignoreEmptyGroup}")
    private boolean wroIgnoreEmptyGroup;

    @Value("${un-idle.wro.ignoreFailingProcessor}")
    private boolean wroIgnoreFailingProcessor;

    @Value("${un-idle.wro.ignoreMissingResources}")
    private boolean wroIgnoreMissingResources;

    @Value("${un-idle.wro.jmxEnabled}")
    private boolean wroJmxEnabled;

    @Value("${un-idle.wro.modelUpdatePeriod}")
    private long wroModelUpdatePeriod;

    @Value("${un-idle.wro.parallelPreprocessing}")
    private boolean wroParallelPreprocessing;

    @Value("${un-idle.wro.resourceWatcherUpdatePeriod}")
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

        final Map<String, Object> jpaProperties = new LinkedHashMap<>();
        jpaProperties.put("net.sf.ehcache.configurationResourceName", hibernateEhcacheConfigurationResourceName);
        jpaProperties.put("hibernate.cache.region.factory_class", hibernateEhcacheRegionFactoryClass);
        jpaProperties.put("hibernate.cache.use_query_cache", hibernateUseQueryCache);
        jpaProperties.put("hibernate.cache.use_second_level_cache", hibernateUseSecondLevelCache);
        jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);

        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(jpaVendorDatabase);

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.un_idle.domain");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages/un-idle");

        return messageSource;
    }

    @Bean
    public SpringLiquibase springLiquibase() {
        final SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setDefaultSchema("public");
        springLiquibase.setChangeLog("classpath:liquibase/changelog.xml");

        return springLiquibase;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setDataSource(dataSource);
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
                                                                                          ServletContextUriLocator.ALIAS_SERVLET_CONTEXT_FIRST,
                                                                                          UrlUriLocator.ALIAS,
                                                                                          ClasspathUriLocator.ALIAS));
        properties.put(ConfigurableNamingStrategy.KEY, TimestampNamingStrategy.ALIAS);
        properties.put(ConfigurableHashStrategy.KEY, SHA1HashStrategy.ALIAS);

        properties.put(ConfigConstants.cacheGzippedContent, wroCacheGzippedContent);
        properties.put(ConfigConstants.cacheUpdatePeriod.name(), cacheUpdatePeriod);
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

    @Configuration
    @Profile("heroku")
    public static class HerokuDatabaseConfiguration {

        @Value("${DATABASE_URL}")
        public URI databaseUri;

        @Bean(destroyMethod = "close")
        public DataSource dataSource() {
            BoneCPDataSource dataSource = new BoneCPDataSource();

            dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl(format("jdbc:postgresql://%s:%d%s",
                                         databaseUri.getHost(),
                                         databaseUri.getPort(),
                                         databaseUri.getPath()));
            dataSource.setUsername(databaseUri.getUserInfo().split(":")[0]);
            dataSource.setPassword(databaseUri.getUserInfo().split(":")[1]);

            return dataSource;
        }

    }

    @Configuration
    @Profile("default")
    public static class LocalDatabaseConfiguration {

        @Value("${un-idle.dataSource.driverClass}")
        private String dataSourceDriverClass;

        @Value("${un-idle.dataSource.password}")
        private String dataSourcePassword;

        @Value("${un-idle.dataSource.url}")
        private String dataSourceUrl;

        @Value("${un-idle.dataSource.username}")
        private String dataSourceUsername;

        @Bean(destroyMethod = "close")
        public DataSource dataSource() {
            BoneCPDataSource dataSource = new BoneCPDataSource();

            dataSource.setDriverClass(dataSourceDriverClass);
            dataSource.setJdbcUrl(dataSourceUrl);
            dataSource.setPassword(dataSourceUsername);
            dataSource.setUsername(dataSourcePassword);

            return dataSource;
        }

    }

}
