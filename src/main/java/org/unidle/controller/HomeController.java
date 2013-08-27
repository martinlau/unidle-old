/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
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
