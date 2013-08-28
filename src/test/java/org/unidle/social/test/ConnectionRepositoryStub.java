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

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.util.MultiValueMap;

import java.util.List;

class ConnectionRepositoryStub implements ConnectionRepository {

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
