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
package org.unidle.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.unidle.domain.User;

import java.util.UUID;

@Service
public class AuditorAwareImpl implements AuditorAware<User> {

    @Autowired
    private final UserRepository userRepository;

    public AuditorAwareImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentAuditor() {

        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            return null;
        }

        final String uuid = authentication.getName();

        return "".equals(uuid)
               ? null
               : userRepository.findOne(UUID.fromString(uuid));
    }

}
