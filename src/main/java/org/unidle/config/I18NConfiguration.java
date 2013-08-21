package org.unidle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.validation.Validator;

@Configuration
@PropertySource("classpath:unidle.properties")
public class I18NConfiguration {

    @Value("${unidle.messageSource.baseName}")
    private String messageSourceBaseName;

    @Value("${unidle.messageSource.cacheSeconds}")
    private int messageSourceCacheSeconds;

    @Value("${unidle.messageSource.encoding}")
    private String messageSourceEncoding;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public Validator validator() {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();

        localValidatorFactoryBean.setValidationMessageSource(messageSource());

        return localValidatorFactoryBean;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding(messageSourceEncoding);
        messageSource.setBasename(messageSourceBaseName);
        messageSource.setCacheSeconds(messageSourceCacheSeconds);

        return messageSource;
    }

}
