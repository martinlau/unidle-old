package org.unidle.social.test;

import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;

import java.util.Set;

public class ConnectionFactoryLocatorStub implements ConnectionFactoryLocator {

    private final ConnectionFactory connectionFactory;

    public ConnectionFactoryLocatorStub() {
        connectionFactory = null;
    }

    public ConnectionFactoryLocatorStub(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory<?> getConnectionFactory(final String providerId) {
        if (connectionFactory != null) {
            return connectionFactory;
        }
        return new ConnectionFactoryStub(providerId);
    }

    @Override
    public <A> ConnectionFactory<A> getConnectionFactory(final Class<A> apiType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> registeredProviderIds() {
        throw new UnsupportedOperationException();
    }

}
