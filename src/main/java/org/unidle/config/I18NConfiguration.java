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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.number.NumberFormatter;
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
    public NumberFormatter numberFormatter() {
        return new NumberFormatter();
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
