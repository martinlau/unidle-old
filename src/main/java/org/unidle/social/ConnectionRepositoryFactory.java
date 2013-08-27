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
package org.unidle.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Component;
import org.unidle.domain.User;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.repository.UserRepository;

import java.util.UUID;

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

    public ConnectionRepository getConnectionRepository(final String userId) {
        final User user = userRepository.findOne(UUID.fromString(userId));

        return new ConnectionRepositoryImpl(connectionFactoryLocator,
                                            textEncryptor,
                                            userConnectionRepository,
                                            user);
    }

}
