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
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

@Configuration
@ComponentScan("org.unidle.social")
@PropertySource("classpath:unidle.properties")
public class SocialConfiguration {

    @Value("${unidle.facebook.clientId}")
    private String facebookClientId;

    @Value("${unidle.facebook.secret}")
    private String facebookSecret;

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
    private UsersConnectionRepository usersConnectionRepository;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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

        final String name = authentication.getName();

        return usersConnectionRepository.createConnectionRepository(name);
    }

    @Bean
    public ProviderSignInController providerSignInController() {
        return new ProviderSignInController(connectionFactoryLocator(),
                                            usersConnectionRepository,
                                            signInAdapter);
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

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.queryableText(textEncryptorPassword,
                                        textEncryptorSalt);
    }

}
