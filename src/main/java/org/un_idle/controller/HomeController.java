package org.un_idle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public LocationFact locationFact(final Location location,
                                     @RequestParam(value = "city",
                                                   defaultValue = "")
                                     final String city,
                                     @RequestParam(value = "subdivision",
                                                   defaultValue = "")
                                     final String subdivision,
                                     @RequestParam(value = "country",
                                                   defaultValue = "")
                                     final String country,
                                     @RequestParam(value = "continent",
                                                   defaultValue = "")
                                     final String continent) {

        return StringUtils.hasText(city)
               || StringUtils.hasText(subdivision)
               || StringUtils.hasText(country)
               || StringUtils.hasText(continent)

               ? locationFactService.findBestFact(city,
                                                  subdivision,
                                                  country,
                                                  continent)

               : locationFactService.findBestFact(location.getCity(),
                                                  location.getSubdivision(),
                                                  location.getCountry(),
                                                  location.getContinent());
    }

}
