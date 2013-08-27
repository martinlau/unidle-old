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
package org.unidle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.LocationFact;
import org.unidle.repository.LocationFactRepository;

@Service
public class LocationFactServiceImpl implements LocationFactService {

    private final LocationFactRepository locationFactRepository;

    @Autowired
    public LocationFactServiceImpl(final LocationFactRepository locationFactRepository) {
        this.locationFactRepository = locationFactRepository;
    }

    @Cacheable("org.unidle.service.LocationFactService")
    @Override
    @Transactional(readOnly = true)
    public LocationFact findBestFact(final String city,
                                     final String subdivision,
                                     final String country,
                                     final String continent) {

        LocationFact locationFact = locationFactRepository.findOne(city, subdivision, country, continent);
        if (locationFact != null) {
            return locationFact;
        }

        locationFact = locationFactRepository.findOne(subdivision, country, continent);
        if (locationFact != null) {
            return locationFact;
        }

        locationFact = locationFactRepository.findOne(country, continent);
        if (locationFact != null) {
            return locationFact;
        }

        return locationFactRepository.findOne(continent);
    }

}
