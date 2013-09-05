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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(final String email, final String firstName, final String lastName) {
        final User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User currentUser() {

        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            return null;
        }

        final String uuid = authentication.getName();

        return userRepository.findOne(UUID.fromString(uuid));
    }

    @Cacheable(value = "org.unidle.service.UserService",
               unless = "#result == false")
    @Override
    @Transactional(readOnly = true)
    public boolean exists(final String email) {
        return userRepository.findOne(email) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCurrentUser(final String email) {
        final User user = userRepository.findOne(email);
        return user != null && user.equals(currentUser());
    }

    @Override
    @Transactional
    public User updateUser(final UUID uuid,
                           final String email,
                           final String firstName,
                           final String lastName) {

        final User user = userRepository.findOne(uuid);

        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return userRepository.save(user);
    }

}
