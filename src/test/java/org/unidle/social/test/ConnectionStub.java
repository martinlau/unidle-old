package org.unidle.social.test;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.AbstractConnection;

public class ConnectionStub<A> extends AbstractConnection<A> {

    private final ConnectionData connectionData;

    private final UserProfile userProfile;

    public ConnectionStub(final UserProfile userProfile) {
        super(null);

        this.connectionData = null;
        this.userProfile = userProfile;
    }

    public ConnectionStub(final ConnectionData connectionData,
                          final UserProfile userProfile) {
        super(connectionData, null);

        this.connectionData = connectionData;
        this.userProfile = userProfile;
    }

    public ConnectionStub(final ConnectionData connectionData) {
        super(connectionData, null);

        this.connectionData = connectionData;
        this.userProfile = null;
    }

    @Override
    public UserProfile fetchUserProfile() {
        return userProfile;
    }

    @Override
    public A getApi() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionData createData() {
        return connectionData;
    }

}
