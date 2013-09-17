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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.repository.UserConnectionRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Sets.newHashSet;

@Repository
public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

    private final ConnectionRepositoryFactory connectionRepositoryFactory;

    private final UserConnectionRepository userConnectionRepository;

    @Autowired
    public UsersConnectionRepositoryImpl(final ConnectionRepositoryFactory connectionRepositoryFactory,
                                         final UserConnectionRepository userConnectionRepository) {

        this.connectionRepositoryFactory = connectionRepositoryFactory;
        this.userConnectionRepository = userConnectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findUserIdsWithConnection(final Connection<?> connection) {
        final ConnectionData connectionData = connection.createData();

        final List<UUID> userIds = userConnectionRepository.findUserIdsWithConnection(connectionData.getProviderId(),
                                                                                      connectionData.getProviderUserId());

        return transform(userIds, toStringFunction());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findUserIdsConnectedTo(final String providerId,
                                              final Set<String> providerUserIds) {

        final List<UUID> userIds = userConnectionRepository.findUserIdsConnectedTo(providerId,
                                                                                   providerUserIds);
        return newHashSet(transform(userIds, toStringFunction()));
    }

    @Override
    @Transactional
    public ConnectionRepository createConnectionRepository(final String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return connectionRepositoryFactory.getConnectionRepository(userId);
    }

}
