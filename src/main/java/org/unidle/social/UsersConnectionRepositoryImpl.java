package org.unidle.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.repository.UserConnectionRepository;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Functions.toStringFunction;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Repository
public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

    private final ConnectionRepositoryFactory connectionRepositoryFactory;

    private final ConnectionSignUp connectionSignUp;

    private final UserConnectionRepository userConnectionRepository;

    @Autowired
    public UsersConnectionRepositoryImpl(final ConnectionRepositoryFactory connectionRepositoryFactory,
                                         final ConnectionSignUp connectionSignUp,
                                         final UserConnectionRepository userConnectionRepository) {

        this.connectionRepositoryFactory = connectionRepositoryFactory;
        this.connectionSignUp = connectionSignUp;
        this.userConnectionRepository = userConnectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findUserIdsWithConnection(final Connection<?> connection) {
        final ConnectionData connectionData = connection.createData();

        final List<Long> userIds = userConnectionRepository.findUserIdsWithConnection(connectionData.getProviderId(),
                                                                                      connectionData.getProviderUserId());

        if (!userIds.isEmpty()) {
            return transform(userIds, toStringFunction());
        }

        if (userIds.isEmpty()) {
            final String userId = connectionSignUp.execute(connection);
            if (userId != null) {

                final ConnectionRepository connectionRepository = createConnectionRepository(userId);
                connectionRepository.addConnection(connection);

                return asList(userId);
            }
        }

        return emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findUserIdsConnectedTo(final String providerId,
                                              final Set<String> providerUserIds) {

        final List<Long> userIds = userConnectionRepository.findUserIdsConnectedTo(providerId,
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
