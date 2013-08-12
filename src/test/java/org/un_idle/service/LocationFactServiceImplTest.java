package org.un_idle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.un_idle.domain.LocationFact;

import static org.fest.assertions.Assertions.assertThat;
import static org.un_idle.test.Conditions.hasFact;
import static org.un_idle.test.Conditions.hasSource;
import static org.un_idle.test.Conditions.hasSummary;

@ContextConfiguration(classes = org.un_idle.config.RootContextConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationFactServiceImplTest {

    @Autowired
    private LocationFactService subject;

    @Test
    public void testFindBestFactWithCity() throws Exception {
        final LocationFact result = subject.findBestFact("Sydney",
                                                         "New South Wales",
                                                         "Australia",
                                                         "Oceania");

        assertThat(result)
                .satisfies(hasFact("Every day in Sydney over 800,000 people spend an average of 75 minutes on public transport"))
                .satisfies(hasSource("Bureau of Transport Statistics, Transport for NSW"))
                .satisfies(hasSummary("That's over 100 years spent checking facebook, reading the paper or staring out the window"));
    }

    @Test
    public void testFindBestFactWithContinent() throws Exception {
        final LocationFact result = subject.findBestFact("",
                                                         "",
                                                         "",
                                                         "Oceania");

        assertThat(result)
                .satisfies(hasFact("A random Oceania fact"))
                .satisfies(hasSource("A random Oceania source"))
                .satisfies(hasSummary("A random Oceania summary"));
    }

    @Test
    public void testFindBestFactWithCountry() throws Exception {
        final LocationFact result = subject.findBestFact("",
                                                         "",
                                                         "Australia",
                                                         "Oceania");

        assertThat(result)
                .satisfies(hasFact("A random Australia fact"))
                .satisfies(hasSource("A random Australia source"))
                .satisfies(hasSummary("A random Australia summary"));
    }

    @Test
    public void testFindBestFactWithNothing() throws Exception {
        final LocationFact result = subject.findBestFact("",
                                                         "",
                                                         "",
                                                         "");

        assertThat(result)
                .satisfies(hasFact("A random Global fact"))
                .satisfies(hasSource("A random Global source"))
                .satisfies(hasSummary("A random Global summary"));
    }

    @Test
    public void testFindBestFactWithSubdivision() throws Exception {
        final LocationFact result = subject.findBestFact("",
                                                         "New South Wales",
                                                         "Australia",
                                                         "Oceania");

        assertThat(result)
                .satisfies(hasFact("A random New South Wales fact"))
                .satisfies(hasSource("A random New South Wales source"))
                .satisfies(hasSummary("A random New South Wales summary"));
    }

}
