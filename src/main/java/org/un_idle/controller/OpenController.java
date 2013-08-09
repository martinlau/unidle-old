package org.un_idle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open")
public class OpenController {

    @RequestMapping
    public String openSource() {
        return ".static.open";
    }

}
