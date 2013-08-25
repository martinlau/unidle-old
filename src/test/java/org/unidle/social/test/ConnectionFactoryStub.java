package org.unidle.social.test;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;

public class ConnectionFactoryStub<A> extends ConnectionFactory<A> {

    private final Connection<A> connection;

    public ConnectionFactoryStub(final String providerId) {
        super(providerId,
              null,
              null);
        this.connection = null;
    }

    public ConnectionFactoryStub(final String providerId,
                                 final Connection<A> connection) {
        super(providerId, null, null);
        this.connection = connection;
    }

    @Override
    public Connection<A> createConnection(final ConnectionData connectionData) {
        if (connection != null) {
            return connection;
        }
        return new ConnectionStub<>(connectionData);
    }

}
