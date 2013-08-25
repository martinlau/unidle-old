package org.unidle.social.test;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class ConnectionRepositoryStub implements ConnectionRepository {

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Connection<?>> findConnections(final String providerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A> List<Connection<A>> findConnections(final Class<A> apiType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(final MultiValueMap<String, String> providerUserIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Connection<?> getConnection(final ConnectionKey connectionKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A> Connection<A> getConnection(final Class<A> apiType, final String providerUserId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(final Class<A> apiType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(final Class<A> apiType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addConnection(final Connection<?> connection) {
        // Do nothing
    }

    @Override
    public void updateConnection(final Connection<?> connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeConnections(final String providerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeConnection(final ConnectionKey connectionKey) {
        throw new UnsupportedOperationException();
    }
}
