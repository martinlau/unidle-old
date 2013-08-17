package org.unidle.social.test;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.AbstractConnection;

public class ConnectionStub extends AbstractConnection<Object> {

    private final ConnectionData connectionData;

    private final UserProfile userProfile;

    public ConnectionStub(final UserProfile userProfile) {
        this(null, userProfile);
    }

    public ConnectionStub(final ConnectionData connectionData,
                          final UserProfile userProfile) {
        super(null);
        this.connectionData = connectionData;
        this.userProfile = userProfile;
    }

    public ConnectionStub(final ConnectionData connectionData) {
        this(connectionData, null);
    }

    @Override
    public UserProfile fetchUserProfile() {
        return userProfile;
    }

    @Override
    public Object getApi() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionData createData() {
        return connectionData;
    }

}
