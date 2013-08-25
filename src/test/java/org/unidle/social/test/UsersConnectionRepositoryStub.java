package org.unidle.social.test;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import java.util.List;
import java.util.Set;

public class UsersConnectionRepositoryStub implements UsersConnectionRepository {

    @Override
    public List<String> findUserIdsWithConnection(final Connection<?> connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> findUserIdsConnectedTo(final String providerId, final Set<String> providerUserIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionRepository createConnectionRepository(final String userId) {
        return new ConnectionRepositoryStub();
    }

}
