package org.un_idle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/about")
    public String about() {
        return ".static.about";
    }

    @RequestMapping("/")
    public String home() {
        return ".static.home";
    }

}
