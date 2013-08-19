package org.unidle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommercialController {

    @RequestMapping("/commercial")
    public String commercial() {
        return ".commercial";
    }

}
