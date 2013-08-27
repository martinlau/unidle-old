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
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

import java.util.List;
import java.util.Set;

public class UsersConnectionRepositoryStub implements UsersConnectionRepository {

    @Override
    public List<String> findUserIdsWithConnection(final Connection<?> connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> findUserIdsConnectedTo(final String providerId, final Set<String> providerUserIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionRepository createConnectionRepository(final String userId) {
        return new ConnectionRepositoryStub();
    }

}
