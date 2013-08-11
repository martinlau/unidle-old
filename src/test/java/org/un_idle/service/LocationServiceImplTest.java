package org.un_idle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.un_idle.geo.Location;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(classes = org.un_idle.config.RootContextConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationServiceImplTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testResolveArgumentForAdelaideAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.146.5");

        assertThat(result.getCity()).isEqualTo("Adelaide");
        assertThat(result.getSubdivision()).isEqualTo("South Australia");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForBrisbaneAustralia() throws Exception {
        final Location result = locationService.locateAddress("132.234.251.230");

        assertThat(result.getCity()).isEqualTo("Brisbane");
        assertThat(result.getSubdivision()).isEqualTo("Queensland");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForCanberraAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.6.77.2");

        assertThat(result.getCity()).isEqualTo("Canberra");
        assertThat(result.getSubdivision()).isEqualTo("Australian Capital Territory");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForDarwinAustralia() throws Exception {
        final Location result = locationService.locateAddress("138.80.0.10");

        assertThat(result.getCity()).isEqualTo("Darwin");
        assertThat(result.getSubdivision()).isEqualTo("Northern Territory");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForHobartAustralia() throws Exception {
        final Location result = locationService.locateAddress("147.41.128.8");

        assertThat(result.getCity()).isEqualTo("Hobart");
        assertThat(result.getSubdivision()).isEqualTo("Tasmania");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForMelbourneAustralia() throws Exception {
        final Location result = locationService.locateAddress("140.159.2.36");

        assertThat(result.getCity()).isEqualTo("Melbourne");
        assertThat(result.getSubdivision()).isEqualTo("Victoria");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForPerthAustralia() throws Exception {
        final Location result = locationService.locateAddress("165.118.1.50");

        assertThat(result.getCity()).isEqualTo("Perth");
        assertThat(result.getSubdivision()).isEqualTo("Western Australia");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForSydneyAustralia() throws Exception {
        final Location result = locationService.locateAddress("203.27.21.6");

        assertThat(result.getCity()).isEqualTo("Sydney");
        assertThat(result.getSubdivision()).isEqualTo("New South Wales");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

}
