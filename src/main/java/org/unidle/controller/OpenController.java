package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OpenController {

    @RequestMapping("/open")
    public String open() {
        return ".open.index";
    }

}
