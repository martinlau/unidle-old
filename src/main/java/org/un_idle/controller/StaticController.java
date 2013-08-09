package org.un_idle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class StaticController {

    @RequestMapping
    public String about() {
        return ".static.about";
    }

}
