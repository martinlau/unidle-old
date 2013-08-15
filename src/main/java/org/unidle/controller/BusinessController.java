package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business")
public class BusinessController {

    @RequestMapping
    public String about() {
        return ".static.business";
    }

}
