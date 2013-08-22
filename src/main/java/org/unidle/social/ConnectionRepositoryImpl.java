package org.unidle.social;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.support.Functions;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private final User user;

    private final UserConnectionRepository userConnectionRepository;

    public ConnectionRepositoryImpl(final ConnectionFactoryLocator connectionFactoryLocator,
                                    final TextEncryptor textEncryptor,
                                    final UserConnectionRepository userConnectionRepository,
                                    final User user) {

        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.userConnectionRepository = userConnectionRepository;
        this.user = user;
    }

    @Override
    @Transactional(readOnly = true)
    public MultiValueMap<String, Connection<?>> findAllConnections() {

        final MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<>();

        final List<UserConnection> userConnections = userConnectionRepository.findAll(user);
        for (final UserConnection userConnection : userConnections) {

            final Connection<?> connection = Functions.toConnection(connectionFactoryLocator, textEncryptor)
                                                      .apply(userConnection);

            connections.add(userConnection.getProviderId(), connection);
        }

        return connections;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Connection<?>> findConnections(final String providerId) {

        final List<UserConnection> userConnections = userConnectionRepository.findAll(user, providerId);

        return transform(userConnections, Functions.toConnection(connectionFactoryLocator, textEncryptor));
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <A> List<Connection<A>> findConnections(final Class<A> apiType) {

        // XXX Don't ask...
        final Object connections = findConnections(getProviderId(apiType));

        return (List<Connection<A>>) connections;
    }

    @Override
    @Transactional(readOnly = true)
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(final MultiValueMap<String, String> providerUserIds) {

        final MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<>();

        for (final String providerId : providerUserIds.keySet()) {

            final List<String> singleProviderUserIds = providerUserIds.get(providerId);
            final List<UserConnection> userConnections = userConnectionRepository.findAll(user, providerId, singleProviderUserIds);

            for (final String providerUserId : singleProviderUserIds) {

                UserConnection userConnection = null;
                for (UserConnection candidateUserConnection : userConnections) {
                    if (providerUserId.equals(candidateUserConnection.getProviderUserId())) {
                        userConnection = candidateUserConnection;
                        break;
                    }
                }

                connections.add(providerId, Functions.toConnection(connectionFactoryLocator, textEncryptor)
                                                     .apply(userConnection));
            }

        }

        return connections;
    }

    @Override
    @Transactional(readOnly = true)
    public Connection<?> getConnection(final ConnectionKey connectionKey) {

        final String providerId = connectionKey.getProviderId();
        final String providerUserId = connectionKey.getProviderUserId();

        final UserConnection userConnection = userConnectionRepository.findOne(user, providerId, providerUserId);

        if (userConnection == null) {
            throw new NoSuchConnectionException(connectionKey);
        }

        final ConnectionData connectionData = Functions.toConnectionData(textEncryptor)
                                                       .apply(userConnection);

        return connectionFactoryLocator.getConnectionFactory(userConnection.getProviderId())
                                       .createConnection(connectionData);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <A> Connection<A> getConnection(final Class<A> apiType,
                                           final String providerUserId) {

        final String providerId = getProviderId(apiType);

        final ConnectionKey connectionKey = new ConnectionKey(providerId, providerUserId);

        return (Connection<A>) getConnection(connectionKey);
    }

    @Override
    @Transactional(readOnly = true)
    public <A> Connection<A> getPrimaryConnection(final Class<A> apiType) {

        final Connection<A> primaryConnection = findPrimaryConnection(apiType);

        if (primaryConnection == null) {
            final String providerId = getProviderId(apiType);
            throw new NotConnectedException(providerId);
        }

        return primaryConnection;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <A> Connection<A> findPrimaryConnection(final Class<A> apiType) {

        final UserConnection userConnection = userConnectionRepository.findPrimaryConnection(user, getProviderId(apiType));

        final ConnectionData connectionData = Functions.toConnectionData(textEncryptor)
                                                       .apply(userConnection);

        if (connectionData == null) {
            return null;
        }

        return (Connection<A>) connectionFactoryLocator.getConnectionFactory(userConnection.getProviderId())
                                                       .createConnection(connectionData);
    }

    @Override
    @Transactional
    public void addConnection(final Connection<?> connection) {

        final ConnectionData connectionData = connection.createData();
        final String providerId = connectionData.getProviderId();
        final String providerUserId = connectionData.getProviderUserId();

        final int rank = userConnectionRepository.findRank(user, providerId);

        final UserConnection userConnection = Functions.toUserConnection(textEncryptor, rank, user)
                                                       .apply(connectionData);

        if (userConnectionRepository.findOne(user, providerId, providerUserId) != null) {
            throw new DuplicateConnectionException(connection.getKey());
        }
        userConnectionRepository.save(userConnection);
    }

    @Override
    @Transactional
    public void updateConnection(final Connection<?> connection) {

        final ConnectionData connectionData = connection.createData();
        final String providerId = connectionData.getProviderId();
        final String providerUserId = connectionData.getProviderUserId();

        final UserConnection userConnection = userConnectionRepository.findOne(user, providerId, providerUserId);

        final UserConnection updatedUserConnection = Functions.toUserConnection(userConnection, textEncryptor, user)
                                                              .apply(connectionData);

        userConnectionRepository.save(updatedUserConnection);
    }

    @Override
    @Transactional
    public void removeConnections(final String providerId) {

        userConnectionRepository.delete(user, providerId);
    }

    @Override
    @Transactional
    public void removeConnection(final ConnectionKey connectionKey) {

        final String providerId = connectionKey.getProviderId();
        final String providerUserId = connectionKey.getProviderUserId();

        userConnectionRepository.delete(user, providerId, providerUserId);
    }

    protected <A> String getProviderId(final Class<A> apiType) {

        return connectionFactoryLocator.getConnectionFactory(apiType)
                                       .getProviderId();
    }

}
