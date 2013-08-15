package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BusinessController {

    @RequestMapping("/business")
    public String business() {
        return ".static.business";
    }

}
