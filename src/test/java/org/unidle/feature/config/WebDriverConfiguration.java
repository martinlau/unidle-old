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
package org.unidle.feature.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@Configuration
@PropertySource("classpath:unidle.properties")
public class WebDriverConfiguration {

    @Value("${tomcat.host}")
    private String host;

    @Value("${tomcat.port}")
    private int port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public URL baseUrl() throws MalformedURLException {
        return new URL(format("http://%s:%d", host, port));
    }

    @Bean
    public Dimension dimension() {
        return new Dimension(320, 568);
    }

    @Bean(destroyMethod = "quit")
    public FirefoxDriver firefoxDriver() {
        final FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);

        return new FirefoxDriver(profile);
    }

}
