package org.unidle.service;

import org.unidle.domain.HasCity;
import org.unidle.domain.HasContinent;
import org.unidle.domain.HasCountry;
import org.unidle.domain.HasSubdivision;

import java.io.Serializable;

public class Location implements Serializable,
                                 HasCity,
                                 HasSubdivision,
                                 HasCountry,
                                 HasContinent {

    private final String city;

    private final String continent;

    private final String country;

    private final String subdivision;

    @SuppressWarnings("unused")
    public Location() {
        this("", "", "", "");
    }

    public Location(final String city,
                    final String subdivision,
                    final String country,
                    final String continent) {
        this.city = city;
        this.subdivision = subdivision;
        this.country = country;
        this.continent = continent;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getContinent() {
        return continent;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getSubdivision() {
        return subdivision;
    }

    @Override
    public String toString() {
        return String.format("Location(city='%s', continent='%s', country='%s', subdivision='%s')",
                             city,
                             continent,
                             country,
                             subdivision);
    }

}
