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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.service.Location.DEFAULT;
import static org.unidle.service.test.Locations.ADELAIDE;
import static org.unidle.service.test.Locations.BRISBANE;
import static org.unidle.service.test.Locations.CANBERRA;
import static org.unidle.service.test.Locations.DARWIN;
import static org.unidle.service.test.Locations.HOBART;
import static org.unidle.service.test.Locations.MELBOURNE;
import static org.unidle.service.test.Locations.PERTH;
import static org.unidle.service.test.Locations.SYDNEY;
import static org.unidle.test.Conditions.hasCity;
import static org.unidle.test.Conditions.hasContinent;
import static org.unidle.test.Conditions.hasCountry;
import static org.unidle.test.Conditions.hasSubdivision;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationServiceImplTest {

    @Autowired
    private LocationService subject;

    @Test
    public void testLocateAddressForAdelaideAustralia() throws Exception {
        final Location result = subject.locateAddress(ADELAIDE.address);

        assertThat(result)
                .satisfies(hasCity("Adelaide"))
                .satisfies(hasSubdivision("South Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForBrisbaneAustralia() throws Exception {
        final Location result = subject.locateAddress(BRISBANE.address);

        assertThat(result)
                .satisfies(hasCity("Brisbane"))
                .satisfies(hasSubdivision("Queensland"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForCanberraAustralia() throws Exception {
        final Location result = subject.locateAddress(CANBERRA.address);

        assertThat(result)
                .satisfies(hasCity("Canberra"))
                .satisfies(hasSubdivision("Australian Capital Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForCommaSeparatedList() throws Exception {
        final Location result = subject.locateAddress(SYDNEY.address + "," + ADELAIDE.address);

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForDarwinAustralia() throws Exception {
        final Location result = subject.locateAddress(DARWIN.address);

        assertThat(result)
                .satisfies(hasCity("Darwin"))
                .satisfies(hasSubdivision("Northern Territory"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForHobartAustralia() throws Exception {
        final Location result = subject.locateAddress(HOBART.address);

        assertThat(result)
                .satisfies(hasCity("Hobart"))
                .satisfies(hasSubdivision("Tasmania"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForInternalAddress() throws Exception {
        final Location result = subject.locateAddress("127.0.0.1");

        assertThat(result)
                .isSameAs(DEFAULT)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry(""))
                .satisfies(hasContinent(""));
    }

    @Test
    public void testLocateAddressForMelbourneAustralia() throws Exception {
        final Location result = subject.locateAddress(MELBOURNE.address);

        assertThat(result)
                .satisfies(hasCity("Melbourne"))
                .satisfies(hasSubdivision("Victoria"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForPerthAustralia() throws Exception {
        final Location result = subject.locateAddress(PERTH.address);

        assertThat(result)
                .satisfies(hasCity("Perth"))
                .satisfies(hasSubdivision("Western Australia"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForSydneyAustralia() throws Exception {
        final Location result = subject.locateAddress(SYDNEY.address);

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testLocateAddressForUnknownAddress() throws Exception {
        final Location result = subject.locateAddress("0.0.0.0");

        assertThat(result)
                .isSameAs(DEFAULT)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry(""))
                .satisfies(hasContinent(""));
    }

    @Test
    public void testLocateAddressWithNull() throws Exception {
        final Location result = subject.locateAddress(null);

        assertThat(result)
                .isSameAs(DEFAULT)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry(""))
                .satisfies(hasContinent(""));
    }

}
