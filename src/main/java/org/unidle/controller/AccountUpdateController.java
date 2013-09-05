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

import com.github.segmentio.models.Traits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.repository.UserRepository;
import org.unidle.service.UserService;

import javax.validation.Valid;

import static com.github.segmentio.Analytics.identify;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AccountUpdateController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/account/update",
                    method = GET)
    public String update(final User user) {
        if (user == null) {
            return "redirect:/signin";
        }
        return ".ajax.account-update";
    }

    @RequestMapping(value = "/account/update",
                    method = POST)
    public String update(@Valid final UserForm userForm,
                         final Errors errors,
                         final User user,
                         final ModelMap modelMap) {

        if (user == null) {
            return "redirect:/signin";
        }

        if (userService.exists(userForm.getEmail()) && !userService.isCurrentUser(userForm.getEmail())) {
            errors.rejectValue("email", "error.email.exists");
        }

        if (errors.hasErrors()) {
            return ".ajax.account-update";
        }

        final User updatedUser = userService.updateUser(user.getUuid(),
                                                        userForm.getEmail(),
                                                        userForm.getFirstName(),
                                                        userForm.getLastName());

        modelMap.addAttribute("user", updatedUser);

        identify(user.getUuid().toString(),
                 new Traits().put("email", updatedUser.getEmail())
                             .put("firstName", updatedUser.getFirstName())
                             .put("lastName", updatedUser.getLastName()));

        return ".ajax.account-updated";
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
