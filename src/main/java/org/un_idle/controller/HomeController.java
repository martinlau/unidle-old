package org.un_idle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.un_idle.geo.Location;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping
    public String home() {
        return ".static.home";
    }

    @ModelAttribute("localFact")
    public String localFact(final Location location) {
        return "Every day in Sydney 500,000 intelligent people spend more than 30 minutes on public transport";
    }

    @ModelAttribute("localSource")
    public String localSource(final Location location) {
        return "2013 Australian Census data";
    }

    @ModelAttribute("localSummary")
    public String localSummary(final Location location) {
        return "That's almost 30 years spent checking facebook, reading the news or staring out the window.";
    }

}
