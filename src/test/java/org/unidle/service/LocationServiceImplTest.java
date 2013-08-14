package org.unidle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.config.RootContextConfiguration;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasCity;
import static org.unidle.test.Conditions.hasContinent;
import static org.unidle.test.Conditions.hasCountry;
import static org.unidle.test.Conditions.hasSubdivision;

@ContextConfiguration(classes = RootContextConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationServiceImplTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testLocateAddressForAdelaideAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.146.5");

        assertThat(result)
                .satisfies(hasCity("Adelaide"))
                .satisfies(hasSubdivision("South Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForBrisbaneAustralia() throws Exception {
        final Location result = locationService.locateAddress("132.234.251.230");

        assertThat(result)
                .satisfies(hasCity("Brisbane"))
                .satisfies(hasSubdivision("Queensland"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForCanberraAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.77.2");

        assertThat(result)
                .satisfies(hasCity("Canberra"))
                .satisfies(hasSubdivision("Australian Capital Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForDarwinAustralia() throws Exception {
        final Location result = locationService.locateAddress("138.80.0.10");

        assertThat(result)
                .satisfies(hasCity("Darwin"))
                .satisfies(hasSubdivision("Northern Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForHobartAustralia() throws Exception {
        final Location result = locationService.locateAddress("147.41.128.8");

        assertThat(result)
                .satisfies(hasCity("Hobart"))
                .satisfies(hasSubdivision("Tasmania"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForMelbourneAustralia() throws Exception {
        final Location result = locationService.locateAddress("140.159.2.36");

        assertThat(result)
                .satisfies(hasCity("Melbourne"))
                .satisfies(hasSubdivision("Victoria"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForPerthAustralia() throws Exception {
        final Location result = locationService.locateAddress("165.118.1.50");

        assertThat(result)
                .satisfies(hasCity("Perth"))
                .satisfies(hasSubdivision("Western Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForSydneyAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.27.21.6");

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForUnknownAddress() throws Exception {
        final Location result = locationService.locateAddress("127.0.0.1");

        assertThat(result)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry(""))
                .satisfies(hasContinent(""));
    }

}