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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

@Controller
public class AccountController {

    private final UserRepository userRepository;

    @Autowired
    public AccountController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/account",
                    method = RequestMethod.GET)
    public String account(final User user) {
        if (user == null) {
            return "redirect:/signin";
        }
        return ".account";
    }

    @ModelAttribute("user")
    public User user(final User user) {
        return user;
    }

}
