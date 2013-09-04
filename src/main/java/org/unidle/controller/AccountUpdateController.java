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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.repository.UserRepository;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AccountUpdateController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/account/update",
                    method = GET)
    public String update() {
        if (SecurityContextHolder.getContext()
                                 .getAuthentication() == null) {
            return "redirect:/signin";
        }
        return ".ajax.account-update";
    }

    @RequestMapping(value = "/account/update",
                    method = POST)
    public String update(@Valid final UserForm userForm,
                         final Errors errors) {

        final User user = user();
        if (user == null) {
            return "redirect:/signin";
        }

        final User userWithEmail = userRepository.findOne(userForm.getEmail());
        if (userWithEmail != null && !userWithEmail.getUuid().equals(user.getUuid())) {
            errors.rejectValue("email", "error.email.exists");
        }

        if (errors.hasErrors()) {
            return ".ajax.account-update";
        }

        // TODO Move this to a service
        user.setEmail(userForm.getEmail());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        userRepository.save(user);

        return ".ajax.account-updated";
    }

    public User user() {
        final Authentication authentication = SecurityContextHolder.getContext()
                                                                   .getAuthentication();

        if (authentication == null) {
            return null;
        }

        final String userId = authentication.getName();

        return userRepository.findOne(UUID.fromString(userId));
    }

    @ModelAttribute("userForm")
    public UserForm userForm() {
        final User user = user();

        return user == null
               ? null
               : new UserForm(user.getEmail(),
                              user.getFirstName(),
                              user.getLastName());
    }

}
