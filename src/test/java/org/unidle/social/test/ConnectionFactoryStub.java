package org.unidle.social.test;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;

public class ConnectionFactoryStub<A> extends ConnectionFactory<A> {

    public ConnectionFactoryStub(final String providerId) {
        super(providerId,
              null,
              null);
    }

    @Override
    public Connection<A> createConnection(final ConnectionData connectionData) {
        return new ConnectionStub<>(connectionData);
    }

}
