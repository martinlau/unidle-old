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
package org.unidle.service;

import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.User;

import java.util.UUID;

public interface UserService {

    User createUser(final String email,
                    final String firstName,
                    final String lastName);

    User currentUser();

    boolean exists(final String email);

    boolean isCurrentUser(String email);

    @Transactional
    User updateUser(UUID uuid,
                    String email,
                    String firstName,
                    String lastName);
}
