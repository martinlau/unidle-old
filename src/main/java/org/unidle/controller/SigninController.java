package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {

    @RequestMapping("/signin")
    public String open() {
        return ".signin";
    }

}
