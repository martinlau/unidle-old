package org.unidle.social.test;

import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;

import java.util.Set;

public class ConnectionFactoryLocatorStub implements ConnectionFactoryLocator {

    @Override
    public ConnectionFactory<?> getConnectionFactory(final String providerId) {
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
