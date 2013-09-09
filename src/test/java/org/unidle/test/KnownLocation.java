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

public enum KnownLocation {

    GLOBAL("Global", "1.0.1.0", "", "", "", ""),

    OCEANIA("Oceania", "14.1.37.0", "Oceania", "", "", ""),

    AUSTRALIA("Australia", "1.0.0.0", "Oceania", "Australia", "", ""),

    WESTERN_AUSTRALIA("Western Australia", "1.44.169.0", "Oceania", "Australia", "Western Australia", ""),

    NORTHERN_TERRITORY("Northern Territory", "1.123.48.0", "Oceania", "Australia", "Northern Territory", ""),

    SOUTH_AUSTRALIA("South Australia", "1.44.177.0", "Oceania", "Australia", "South Australia", ""),

    QUEENSLAND("Queensland", "1.44.12.0", "Oceania", "Australia", "Queensland", ""),

    NEW_SOUTH_WALES("New South Wales", "1.44.9.0", "Oceania", "Australia", "New South Wales", ""),

    AUSTRALIAN_CAPITAL_TERRITORY("Australian Capital Territory", "49.180.153.0", "Oceania", "Australia", "Australian Capital Territory", ""),

    VICTORIA("Victoria", "1.44.157.0", "Oceania", "Australia", "Victoria", ""),

    TASMANIA("Tasmania", "42.241.51.0", "Oceania", "Australia", "Tasmania", ""),

    ADELAIDE("Adelaide", "1.44.153.0", "Oceania", "Australia", "South Australia", "Adelaide"),

    BRISBANE("Brisbane", "1.44.81.0", "Oceania", "Australia", "Queensland", "Brisbane"),

    CANBERRA("Canberra", "14.202.197.0", "Oceania", "Australia", "Australian Capital Territory", "Canberra"),

    DARWIN("Darwin", "1.123.33.0", "Oceania", "Australia", "Northern Territory", "Darwin"),

    HOBART("Hobart", "58.6.102.0", "Oceania", "Australia", "Tasmania", "Hobart"),

    MELBOURNE("Melbourne", "1.0.5.0", "Oceania", "Australia", "Victoria", "Melbourne"),

    PERTH("Perth", "1.44.219.0", "Oceania", "Australia", "Western Australia", "Perth"),

    SYDNEY("Sydney", "1.44.22.0", "Oceania", "Australia", "New South Wales", "Sydney");

    public final String address;

    public final String city;

    public final String continent;

    public final String country;

    public final String display;

    public final String subdivision;

    KnownLocation(final String display,
                  final String address,
                  final String continent,
                  final String country,
                  final String subdivision,
                  final String city) {

        this.display = display;
        this.address = address;
        this.continent = continent;
        this.country = country;
        this.subdivision = subdivision;
        this.city = city;
    }

    public static KnownLocation byDisplay(final String display) {
        for (KnownLocation location : values()) {
            if (location.display.equals(display)) {
                return location;
            }
        }
        throw new IllegalArgumentException(display);
    }

}
