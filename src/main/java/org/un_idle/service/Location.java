package org.un_idle.service;

import java.io.Serializable;

public class Location implements Serializable {

    private final String city;

    private final String continent;

    private final String country;

    private final String subdivision;

    @SuppressWarnings("unused")
    public Location() {
        this("",
             "",
             "",
             "");
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

    public String getCity() {
        return city;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public String getSubdivision() {
        return subdivision;
    }

}
