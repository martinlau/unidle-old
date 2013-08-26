package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostConnectController {

    @RequestMapping("/connect/facebookConnected")
    public String postFacebook() {
        return "redirect:/account";
    }

    @RequestMapping("/connect/twitterConnected")
    public String postTwitter() {
        return "redirect:/account";
    }

}
