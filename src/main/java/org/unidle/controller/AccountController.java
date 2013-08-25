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
