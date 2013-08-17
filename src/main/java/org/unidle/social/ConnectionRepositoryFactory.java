package org.unidle.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.stereotype.Component;
import org.unidle.domain.User;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.repository.UserRepository;

@Component
public class ConnectionRepositoryFactory {

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private final UserConnectionRepository userConnectionRepository;

    private final UserRepository userRepository;

    @Autowired
    public ConnectionRepositoryFactory(final ConnectionFactoryLocator connectionFactoryLocator,
                                       final TextEncryptor textEncryptor,
                                       final UserConnectionRepository userConnectionRepository,
                                       final UserRepository userRepository) {

        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.userConnectionRepository = userConnectionRepository;
        this.userRepository = userRepository;
    }

    public ConnectionRepositoryImpl getConnectionRepository(final String userId) {
        final User user = userRepository.findOne(Long.valueOf(userId));

        return new ConnectionRepositoryImpl(connectionFactoryLocator,
                                            textEncryptor,
                                            userConnectionRepository,
                                            user);
    }

}
