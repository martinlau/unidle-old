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
