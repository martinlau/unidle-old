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
