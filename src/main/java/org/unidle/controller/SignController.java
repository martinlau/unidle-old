package org.unidle.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.unidle.form.SignupForm;
import org.unidle.service.UserService;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SignController {

    private final UserService userService;

    @Autowired
    public SignController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = GET,
                    value = "/signup")
    public String signup() {
        return ".signup.index";
    }

    @ModelAttribute("signupForm")
    public SignupForm signupForm(final WebRequest webRequest) {

        final Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);

        if (connection == null) {
            return new SignupForm();
        }

        final UserProfile userProfile = connection.fetchUserProfile();

        return new SignupForm(userProfile.getEmail(),
                              userProfile.getFirstName(),
                              userProfile.getLastName());
    }

    @RequestMapping(method = POST,
                    value = "/signup")
    @Transactional
    public String submit(@Valid final SignupForm signupForm,
                         final Errors errors,
                         final WebRequest webRequest) {

        if (errors.hasErrors()) {
            return ".signup.index";
        }

        if (userService.exists(signupForm.getEmail())) {
            errors.rejectValue("email", "errors.email.exists");

            return ".signup.index";
        }

        final User user = userService.createUser(signupForm.getEmail(), signupForm.getFirstName(),
                                                 signupForm.getLastName()
                                                );

        ProviderSignInUtils.handlePostSignUp(user.getId().toString(),
                                             webRequest);

        return "redirect:/";
    }

}
