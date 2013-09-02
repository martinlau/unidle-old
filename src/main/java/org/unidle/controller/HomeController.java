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
import org.springframework.context.MessageSource;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.LocationFact;
import org.unidle.service.Location;
import org.unidle.service.LocationFactService;

import java.util.Locale;

import static org.springframework.util.StringUtils.hasText;

@Controller
public class HomeController {

    private final LocationFactService locationFactService;

    private final MessageSource messageSource;

    private final NumberFormatter numberFormatter;

    @Autowired
    public HomeController(final LocationFactService locationFactService,
                          final MessageSource messageSource,
                          final NumberFormatter numberFormatter) {

        this.locationFactService = locationFactService;
        this.messageSource = messageSource;
        this.numberFormatter = numberFormatter;
    }

    @ModelAttribute("fact")
    public String fact(final Location location,
                       final Locale locale) {
        final LocationFact locationFact = getBestFact(location);

        final String timeUnit = messageSource.getMessage(locationFact.getTaskTimeUnit().getMessageKey(), null, locale);

        final String locationPrefix = hasText(locationFact.getLocationPrefix())
                                      ? messageSource.getMessage(locationFact.getLocationPrefix(), null, locale)
                                      : "";

        final String locationName = hasText(locationFact.getLocationName())
                                    ? locationFact.getLocationName()
                                    : "";

        final String people = numberFormatter.print(locationFact.getTaskPeople(), locale);

        final String duration = numberFormatter.print(locationFact.getTaskDuration(), locale);

        final String durationTimeUnit = locationFact.getTaskDuration() == 1
                                        ? messageSource.getMessage(locationFact.getTaskDurationTimeUnit().getMessageKey(), null, locale)
                                        : messageSource.getMessage(locationFact.getTaskDurationTimeUnit().getMessageKey() + ".plural", null, locale);

        final String task = messageSource.getMessage(locationFact.getTaskCode(), null, locale);

        final Object[] args = new Object[]{
                timeUnit,
                locationPrefix,
                locationName,
                people,
                duration,
                durationTimeUnit,
                task
        };

        return locationFact.getTaskPeople() == 1
               ? messageSource.getMessage("home.fact", args, locale)
               : messageSource.getMessage("home.fact.plural", args, locale);
    }

    protected LocationFact getBestFact(final Location location) {
        return locationFactService.findBestFact(location.getCity(),
                                                location.getSubdivision(),
                                                location.getCountry(),
                                                location.getContinent());
    }

    @RequestMapping("/")
    public String home() {
        return ".home";
    }

    @ModelAttribute("source")
    public String source(final Location location) {
        return getBestFact(location).getSource();
    }

    @ModelAttribute("summary")
    public String summary(final Location location,
                          final Locale locale) {

        final LocationFact locationFact = getBestFact(location);

        final String duration = numberFormatter.print(locationFact.getSummaryDuration(), locale);

        final String durationUnit = locationFact.getSummaryDuration() == 1
                                    ? messageSource.getMessage(locationFact.getSummaryDurationTimeUnit().getMessageKey(), null, locale)
                                    : messageSource.getMessage(locationFact.getSummaryDurationTimeUnit().getMessageKey() + ".plural", null, locale);

        final Object[] args = {
                duration,
                durationUnit
        };

        return messageSource.getMessage("home.summary", args, locale);
    }

}
