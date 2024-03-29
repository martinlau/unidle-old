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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.analytics.Analytics;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.service.UserService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UpdateAccountController {

    private final Analytics analytics;

    private final UserService userService;

    @Autowired
    public UpdateAccountController(final Analytics analytics,
                                   final UserService userService) {

        this.analytics = analytics;
        this.userService = userService;
    }

    @RequestMapping(value = "/account/update",
                    method = GET)
    public String update(final User user) {

        // TODO Put the following snippet somewhere reusable or use spring-security for protection
        if (user == null) {
            return "redirect:/signin";
        }
        return ".ajax.update-account";
    }

    @RequestMapping(value = "/account/update",
                    method = POST)
    public String update(@Valid final UserForm userForm,
                         final Errors errors,
                         final User user,
                         final ModelMap modelMap) {

        // TODO Put the following snippet somewhere reusable or use spring-security for protection
        if (user == null) {
            return "redirect:/signin";
        }

        if (userService.exists(userForm.getEmail()) && !userService.isCurrentUser(userForm.getEmail())) {
            errors.rejectValue("email", "errors.email.exists");
        }

        if (errors.hasErrors()) {
            return ".ajax.update-account";
        }

        final User updatedUser = userService.updateUser(user.getUuid(),
                                                        userForm.getEmail(),
                                                        userForm.getFirstName(),
                                                        userForm.getLastName());

        modelMap.addAttribute("user", updatedUser);

        analytics.identify(user.getUuid(),
                           "email", updatedUser.getEmail(),
                           "firstName", updatedUser.getFirstName(),
                           "lastName", updatedUser.getLastName());

        return ".ajax.updated-account";
    }

    @ModelAttribute("userForm")
    public UserForm userForm(final User user) {
        return user == null
               ? null
               : new UserForm(user.getEmail(),
                              user.getFirstName(),
                              user.getLastName());
    }

}
