package org.un_idle.config;

import com.jolbox.bonecp.BoneCPDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
import org.un_idle.service.LocationService;
import ro.isdc.wro.extensions.processor.css.RubySassCssProcessor;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@ComponentScan("org.un_idle.service")
@Configuration
@EnableJpaRepositories(basePackages = "org.un_idle.repository")
@EnableTransactionManagement
public class RootContextConfiguration {

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
        final Map<String, Object> jpaProperties = new LinkedHashMap<>();

        jpaProperties.put("net.sf.ehcache.configurationResourceName",
                          "/ehcache/ehcache.xml");
        jpaProperties.put("hibernate.cache.region.factory_class",
                          "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        jpaProperties.put("hibernate.cache.use_query_cache",
                          "true");
        jpaProperties.put("hibernate.cache.use_second_level_cache",
                          "true");
        jpaProperties.put("hibernate.hbm2ddl.auto",
                          "validate");

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabase(Database.H2);

        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.un_idle.domain");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:un-idle;MVCC=TRUE");
        dataSource.setPassword("un-idle");
        dataSource.setUsername("un-idle");

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
        messageSource.setBasename("messages.un-idle");

        return messageSource;
    }

    @Bean
    public SpringLiquibase springLiquibase() {
        final SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource());
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

    @Bean(destroyMethod = "destroy")
    public WroFilter wroFilter() {
        final ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();

        wroFilter.setWroManagerFactory(wroManagerFactory());
        wroFilter.setCacheUpdatePeriod(5L);
        wroFilter.setDebug(false);
        wroFilter.setEncoding("UTF-8");

        return wroFilter;
    }

    @Bean(destroyMethod = "destroy")
    public WroManagerFactory wroManagerFactory() {
        final Properties properties = new Properties();

        properties.put("postProcessors",
                       RubySassCssProcessor.ALIAS);

        final ConfigurableProcessorsFactory processorsFactory = new ConfigurableProcessorsFactory();

        processorsFactory.setProperties(properties);

        final ConfigurableWroManagerFactory wroManagerFactory = new ConfigurableWroManagerFactory();

        wroManagerFactory.setProcessorsFactory(processorsFactory);

        return wroManagerFactory;
    }

}
