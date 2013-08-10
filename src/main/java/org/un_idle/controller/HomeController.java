package org.un_idle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.un_idle.domain.LocationFact;
import org.un_idle.geo.Location;
import org.un_idle.service.LocationFactService;

@Controller
@RequestMapping("/")
public class HomeController {

    private final LocationFactService locationFactService;

    @Autowired
    public HomeController(final LocationFactService locationFactService) {
        this.locationFactService = locationFactService;
    }

    @RequestMapping
    public String home() {
        return ".static.home";
    }

    @ModelAttribute("locationFact")
    public LocationFact locationFact(final Location location) {
        return locationFactService.findBestFact(location.getCity(),
                                                location.getSubdivision(),
                                                location.getCountry(),
                                                location.getContinent());
    }

}
