package org.unidle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.unidle.domain.User;
import org.unidle.form.UserForm;
import org.unidle.service.UserService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET,
                    value = "/signup")
    public String signup() {
        return ".signup";
    }

    @RequestMapping(method = POST,
                    value = "/signup")
    @Transactional
    public String submit(@Valid final UserForm userForm,
                         final Errors errors,
                         final WebRequest webRequest) {

        if (errors.hasErrors()) {
            return ".signup";
        }

        if (userService.exists(userForm.getEmail())) {
            errors.rejectValue("email", "errors.email.exists");

            return ".signup";
        }

        final User user = userService.createUser(userForm.getEmail(), userForm.getFirstName(),
                                                 userForm.getLastName()
                                                );

        ProviderSignInUtils.handlePostSignUp(user.getId().toString(),
                                             webRequest);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId().toString(), null);

        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        return "redirect:/account";
    }

    @ModelAttribute("userForm")
    public UserForm userForm(final WebRequest webRequest) {

        final Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);

        if (connection == null) {
            return new UserForm();
        }

        final UserProfile userProfile = connection.fetchUserProfile();

        return new UserForm(userProfile.getEmail(),
                            userProfile.getFirstName(),
                            userProfile.getLastName());
    }

}
