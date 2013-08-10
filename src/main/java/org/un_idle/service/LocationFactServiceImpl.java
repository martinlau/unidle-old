package org.un_idle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.un_idle.domain.LocationFact;
import org.un_idle.repository.LocationFactRepository;

@Service
public class LocationFactServiceImpl implements LocationFactService {

    private final LocationFactRepository locationFactRepository;

    @Autowired
    public LocationFactServiceImpl(final LocationFactRepository locationFactRepository) {
        this.locationFactRepository = locationFactRepository;
    }

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
