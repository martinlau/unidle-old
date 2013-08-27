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
package org.unidle.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.unidle.domain.User;
import org.unidle.repository.AuditorAwareImpl;
import org.unidle.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "org.unidle.repository")
@EnableTransactionManagement
@ImportResource("classpath:spring/jpa-audit.xml")
@PropertySource("classpath:unidle.properties")
public class DataConfiguration {

    @Value("${unidle.dataSource.driverClass}")
    private String dataSourceDriverClass;

    @Value("${unidle.dataSource.password}")
    private String dataSourcePassword;

    @Value("${unidle.dataSource.url}")
    private String dataSourceUrl;

    @Value("${unidle.dataSource.username}")
    private String dataSourceUsername;

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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Autowired
    public AuditorAware<User> auditorAware(UserRepository userRepository) {
        return new AuditorAwareImpl(userRepository);
    }

    @Bean
    public FactoryBean<EntityManager> entityManager() {
        final EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();

        final SharedEntityManagerBean entityManagerBean = new SharedEntityManagerBean();

        entityManagerBean.setEntityManagerFactory(entityManagerFactory);

        return entityManagerBean;
    }

    @Bean
    @DependsOn({"cacheManager",
                "springLiquibase"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabase(jpaVendorDatabase);

        final Map<String, Object> jpaProperties = new LinkedHashMap<String, Object>();

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
        entityManagerFactoryBean.setMappingResources("jpa/orm.xml");

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
    public SpringLiquibase springLiquibase() {
        final SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource());
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

}
