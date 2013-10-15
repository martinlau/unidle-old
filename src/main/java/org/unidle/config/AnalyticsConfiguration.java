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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.unidle.analytics.AnalyticsImpl;
import org.unidle.controller.RedirectingConnectController;
import org.unidle.service.UserService;
import org.unidle.social.AnalyticTrackingConnectInterceptor;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

@Configuration
@PropertySource("classpath:unidle.properties")
public class AnalyticsConfiguration {

    @Value("${unidle.segment.io.apiKey}")
    private String segmentIoApiKey;

    @Value("${unidle.segment.io.secret}")
    private String segmentIoSecret;

    @Autowired
    private UserService userService;

    @Bean
    public ConnectInterceptor<?> analyticTrackingConnectInterceptor() {
        return new AnalyticTrackingConnectInterceptor<>(analyticsImpl(), userService);
    }

    @Bean
    public AnalyticsImpl analyticsImpl() {
        return new AnalyticsImpl(segmentIoApiKey);
    }

}
