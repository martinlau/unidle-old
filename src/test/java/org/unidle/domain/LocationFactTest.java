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
package org.unidle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LocationFactTest {

    private LocationFact subject;

    @Before
    public void setUp() throws Exception {
        subject = new LocationFact();
    }

    @Test
    public void testGetLocationWithCity() throws Exception {
        subject.setCity("city");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("city");
    }

    @Test
    public void testGetLocationWithSubdivision() throws Exception {
        subject.setSubdivision("subdivision");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("subdivision");
    }

    @Test
    public void testGetLocationWithCountry() throws Exception {
        subject.setCountry("country");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("country");
    }

    @Test
    public void testGetLocationWithContent() throws Exception {
        subject.setContinent("continent");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("continent");
    }

    @Test
    public void testGetLocationWithNothing() throws Exception {
        final String result = subject.getLocation();

        assertThat(result).isNull();
    }

}
