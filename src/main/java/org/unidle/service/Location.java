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

import java.io.Serializable;

public class Location implements Serializable {

    private final String city;

    private final String continent;

    private final String country;

    private final String subdivision;

    public static final Location DEFAULT = new Location("",
                                                        "",
                                                        "",
                                                        "");

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

    @Override
    public String toString() {
        return String.format("Location(city='%s', continent='%s', country='%s', subdivision='%s')",
                             city,
                             continent,
                             country,
                             subdivision);
    }

}
