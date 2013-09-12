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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:unidle.properties")
public class SocialCredentialsConfiguration {

    @Value("${facebook.password}")
    private String facebookPassword;

    @Value("${facebook.username}")
    private String facebookUsername;

    @Value("${twitter.password}")
    private String twitterPassword;

    @Value("${twitter.username}")
    private String twitterUsername;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public String facebookPassword() {
        return facebookPassword;
    }

    @Bean
    public String facebookUsername() {
        return facebookUsername;
    }

    @Bean
    public String twitterPassword() {
        return twitterPassword;
    }

    @Bean
    public String twitterUsername() {
        return twitterUsername;
    }

}
