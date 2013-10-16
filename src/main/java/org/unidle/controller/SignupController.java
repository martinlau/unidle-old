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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.unidle.analytics.Analytics;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.service.UserService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.unidle.analytics.AnalyticsEvent.SIGN_UP;
import static org.unidle.analytics.AnalyticsEvent.TRAIT_CREATED;
import static org.unidle.analytics.AnalyticsEvent.TRAIT_EMAIL;
import static org.unidle.analytics.AnalyticsEvent.TRAIT_FIRST_NAME;
import static org.unidle.analytics.AnalyticsEvent.TRAIT_LAST_NAME;

@Controller
public class SignupController {

    private final Analytics analytics;

    private final UserService userService;

    @Autowired
    public SignupController(final Analytics analytics,
                            final UserService userService) {
        this.analytics = analytics;
        this.userService = userService;
    }

    @RequestMapping(method = GET,
                    value = "/signup")
    public String signup(final WebRequest webRequest) {

        final Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);

        if (connection == null) {
            return "redirect:/signin";
        }

        return ".signup";
    }

    @RequestMapping(method = POST,
                    value = "/signup")
    public String submit(@Valid final UserForm userForm,
                         final Errors errors,
                         final WebRequest webRequest) {

        final Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);

        if (connection == null) {
            return "redirect:/signin";
        }

        if (errors.hasErrors()) {
            return ".signup";
        }

        if (userService.exists(userForm.getEmail())) {
            errors.rejectValue("email", "errors.email.exists");

            return ".signup";
        }

        final User user = userService.createUser(userForm.getEmail(),
                                                 userForm.getFirstName(),
                                                 userForm.getLastName());

        ProviderSignInUtils.handlePostSignUp(user.getUuid().toString(),
                                             webRequest);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), null);

        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        analytics.identify(user.getUuid(),
                           TRAIT_CREATED, user.getCreatedDate().toDate(),
                           TRAIT_EMAIL, user.getEmail(),
                           TRAIT_FIRST_NAME, user.getFirstName(),
                           TRAIT_LAST_NAME, user.getLastName());

        analytics.track(user.getUuid(), SIGN_UP);

        return "redirect:/account";
    }

    @ModelAttribute("userForm")
    public UserForm userForm(final WebRequest webRequest) {

        final Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);

        if (connection == null) {
            return new UserForm();
        }

        final UserProfile userProfile = connection.fetchUserProfile();

        return userProfile == null
               ? new UserForm()
               : new UserForm(userProfile.getEmail(),
                              userProfile.getFirstName(),
                              userProfile.getLastName());
    }

}
