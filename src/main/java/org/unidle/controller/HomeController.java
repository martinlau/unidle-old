package org.unidle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.LocationFact;
import org.unidle.service.Location;
import org.unidle.service.LocationFactService;

@Controller
public class HomeController {

    private final LocationFactService locationFactService;

    @Autowired
    public HomeController(final LocationFactService locationFactService) {
        this.locationFactService = locationFactService;
    }

    @RequestMapping("/")
    public String home() {
        return ".home";
    }

    @ModelAttribute("locationFact")
    public LocationFact locationFact(final Location location) {
        return locationFactService.findBestFact(location.getCity(),
                                                location.getSubdivision(),
                                                location.getCountry(),
                                                location.getContinent());
    }

}
