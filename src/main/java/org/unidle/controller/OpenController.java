package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open")
public class OpenController {

    @RequestMapping
    public String open() {
        return ".static.open";
    }

}