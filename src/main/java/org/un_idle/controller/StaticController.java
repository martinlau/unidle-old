package org.un_idle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/")
    public String home() {
        return ".static.home";
    }

    @RequestMapping("/about")
    public String about() {
        return ".static.about";
    }

    @RequestMapping("/sign-in")
    public String signIn() {
        return ".static.sign-in";
    }

}
