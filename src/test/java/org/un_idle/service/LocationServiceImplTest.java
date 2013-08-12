package org.un_idle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.fest.assertions.Assertions.assertThat;
import static org.un_idle.test.Conditions.hasCity;
import static org.un_idle.test.Conditions.hasContinent;
import static org.un_idle.test.Conditions.hasCountry;
import static org.un_idle.test.Conditions.hasSubdivision;

@ContextConfiguration(classes = org.un_idle.config.RootContextConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationServiceImplTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testResolveArgumentForAdelaideAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.146.5");

        assertThat(result)
                .satisfies(hasCity("Adelaide"))
                .satisfies(hasSubdivision("South Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForBrisbaneAustralia() throws Exception {
        final Location result = locationService.locateAddress("132.234.251.230");

        assertThat(result)
                .satisfies(hasCity("Brisbane"))
                .satisfies(hasSubdivision("Queensland"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForCanberraAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.77.2");

        assertThat(result)
                .satisfies(hasCity("Canberra"))
                .satisfies(hasSubdivision("Australian Capital Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForDarwinAustralia() throws Exception {
        final Location result = locationService.locateAddress("138.80.0.10");

        assertThat(result)
                .satisfies(hasCity("Darwin"))
                .satisfies(hasSubdivision("Northern Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForHobartAustralia() throws Exception {
        final Location result = locationService.locateAddress("147.41.128.8");

        assertThat(result)
                .satisfies(hasCity("Hobart"))
                .satisfies(hasSubdivision("Tasmania"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForMelbourneAustralia() throws Exception {
        final Location result = locationService.locateAddress("140.159.2.36");

        assertThat(result)
                .satisfies(hasCity("Melbourne"))
                .satisfies(hasSubdivision("Victoria"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForPerthAustralia() throws Exception {
        final Location result = locationService.locateAddress("165.118.1.50");

        assertThat(result)
                .satisfies(hasCity("Perth"))
                .satisfies(hasSubdivision("Western Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForSydneyAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.27.21.6");

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

}
