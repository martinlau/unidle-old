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
import org.unidle.controller.RedirectingConnectController;
import org.unidle.service.UserService;
import org.unidle.social.AnalyticTrackingConnectInterceptor;

import javax.annotation.PostConstruct;

import static com.github.segmentio.Analytics.initialize;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

@Configuration
@ComponentScan("org.unidle.social")
@EnableTransactionManagement
@PropertySource("classpath:unidle.properties")
public class SocialConfiguration {

    @Value("${unidle.facebook.clientId}")
    private String facebookClientId;

    @Value("${unidle.facebook.secret}")
    private String facebookSecret;

    @Value("${unidle.segment.io.secret}")
    private String segmentIoSecret;

    @Autowired
    private SignInAdapter signInAdapter;

    @Value("${unidle.textEncryptor.password}")
    private String textEncryptorPassword;

    @Value("${unidle.textEncryptor.salt}")
    private String textEncryptorSalt;

    @Value("${unidle.twitter.consumerKey}")
    private String twitterConsumerKey;

    @Value("${unidle.twitter.consumerSecret}")
    private String twitterConsumerSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ConnectController connectController() {
        final RedirectingConnectController connectController = new RedirectingConnectController(connectionFactoryLocator(),
                                                                                                connectionRepository());

        connectController.addInterceptor(analyticTrackingConnectInterceptor());

        return connectController;
    }

    @Bean
    public ConnectInterceptor<?> analyticTrackingConnectInterceptor() {
        return new AnalyticTrackingConnectInterceptor<>(userService);
    }

    @Bean
    @Scope(value = "request",
           proxyMode = INTERFACES)
    public ConnectionRepository connectionRepository() {
        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }

        final String uuid = authentication.getName();

        return usersConnectionRepository.createConnectionRepository(uuid);
    }

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

        registry.addConnectionFactory(new FacebookConnectionFactory(facebookClientId,
                                                                    facebookSecret));
        registry.addConnectionFactory(new TwitterConnectionFactory(twitterConsumerKey,
                                                                   twitterConsumerSecret));

        return registry;
    }

    @PostConstruct
    public void initializeAnalytics() {
        initialize(segmentIoSecret);
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        final ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator(),
                                                                                               usersConnectionRepository,
                                                                                               signInAdapter);

        providerSignInController.setPostSignInUrl("/account");

        return providerSignInController;
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.queryableText(textEncryptorPassword,
                                        textEncryptorSalt);
    }

}
