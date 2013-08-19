package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TermsController {

    @RequestMapping("/terms")
    public String terms() {
        return ".terms";
    }

}
