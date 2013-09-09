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
package org.unidle.test;

public enum Locations {

    GLOBAL("1.0.1.0", "", "", "", ""),

    OCEANIA("14.1.37.0", "Oceania", "", "", ""),

    AUSTRALIA("1.0.0.0", "Oceania", "Australia", "", ""),

    WESTERN_AUSTRALIA("1.44.169.0", "Oceania", "Australia", "Western Australia", ""),

    NORTHERN_TERRITORY("1.123.48.0", "Oceania", "Australia", "Northern Territory", ""),

    SOUTH_AUSTRALIA("1.44.177.0", "Oceania", "Australia", "South Australia", ""),

    QUEENSLAND("1.44.12.0", "Oceania", "Australia", "Queensland", ""),

    NEW_SOUTH_WALES("1.44.9.0", "Oceania", "Australia", "New South Wales", ""),

    AUSTRALIAN_CAPITAL_TERRITORY("49.180.153.0", "Oceania", "Australia", "Australian Capital Territory", ""),

    VICTORIA("1.44.157.0", "Oceania", "Australia", "Victoria", ""),

    TASMANIA("42.241.51.0", "Oceania", "Australia", "Tasmania", ""),

    ADELAIDE("1.44.153.0", "Oceania", "Australia", "South Australia", "Adelaide"),

    BRISBANE("1.44.81.0", "Oceania", "Australia", "Queensland", "Brisbane"),

    CANBERRA("14.202.197.0", "Oceania", "Australia", "Australian Capital Territory", "Canberra"),

    DARWIN("1.123.33.0", "Oceania", "Australia", "Northern Territory", "Darwin"),

    HOBART("58.6.102.0", "Oceania", "Australia", "Tasmania", "Hobart"),

    MELBOURNE("1.0.5.0", "Oceania", "Australia", "Victoria", "Melbourne"),

    PERTH("1.44.219.0", "Oceania", "Australia", "Western Australia", "Perth"),

    SYDNEY("1.44.22.0", "Oceania", "Australia", "New South Wales", "Sydney");

    public final String address;

    public final String city;

    public final String continent;

    public final String country;

    public final String subdivision;

    Locations(final String address,
              final String continent,
              final String country,
              final String subdivision,
              final String city) {

        this.address = address;
        this.continent = continent;
        this.country = country;
        this.subdivision = subdivision;
        this.city = city;

    }

}
