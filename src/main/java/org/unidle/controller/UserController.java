package org.unidle.controller;

import de.bripkens.gravatar.Gravatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UserController {

    @Autowired
    private Gravatar gravatar;

    @ModelAttribute("gravatar")

    public Gravatar gravatar() {
        return gravatar;
    }

    @RequestMapping("/user/{id}")
    public String user(@PathVariable("id") final Long id,
                       final ModelMap modelMap) {

        // TODO Lookup user properly
        if (id == 1L) {
            modelMap.addAttribute("user", new MockUser(1L,
                                                       "Martin Lau",
                                                       "m@hum.ph",
                                                       "martin.s.lau",
                                                       "hum_ph"));
        }
        else if (id == 2L) {
            modelMap.addAttribute("user", new MockUser(2L,
                                                       "Kate Lau",
                                                       "kdotl@hum.ph",
                                                       "",
                                                       "th3littleredhen"));
        }
        else {
            throw new UserNotFoundException();
        }

        return ".user.view";
    }

    public static class MockUser {

        private final String email;

        private final Long id;

        private final String name;

        private final String facebook;

        private final String twitter;

        public MockUser(final Long id,
                        final String name,
                        final String email,
                        final String facebook,
                        final String twitter) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.facebook = facebook;
            this.twitter = twitter;
        }

        public String getFacebook() {
            return facebook;
        }

        public String getEmail() {
            return email;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTwitter() {
            return twitter;
        }

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException {

        // Nothing to see here

    }

}
