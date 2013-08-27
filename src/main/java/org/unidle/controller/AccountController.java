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
package org.unidle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.repository.UserRepository;

import java.util.UUID;

@Controller
public class AccountController {

    private final UserRepository userRepository;

    @Autowired
    public AccountController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/account")
    public String account() {
        // TODO Check for a non logged in user
        return ".account";
    }

    @ModelAttribute("userForm")
    public UserForm userForm() {
        final User user = user();

        return user == null
               ? new UserForm()
               : new UserForm(user.getEmail(),
                              user.getFirstName(),
                              user.getLastName());
    }

    @ModelAttribute("user")
    public User user() {
        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            return null;
        }

        final String userId = authentication.getName();

        return userRepository.findOne(UUID.fromString(userId));
    }

}
