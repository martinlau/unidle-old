/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
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
