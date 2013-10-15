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
package org.unidle.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.domain.LocationFact;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasCity;
import static org.unidle.test.Conditions.hasContinent;
import static org.unidle.test.Conditions.hasCountry;
import static org.unidle.test.Conditions.hasSubdivision;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LocationFactRepositoryTest {

    @Autowired
    private LocationFactRepository subject;

    @Test
    public void testFindOneWithCity() throws Exception {
        final LocationFact result = subject.findOne("Sydney", "New South Wales", "Australia", "Oceania");

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testFindOneWithContinent() throws Exception {
        final LocationFact result = subject.findOne("Oceania");

        assertThat(result)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry(""))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testFindOneWithCountry() throws Exception {
        final LocationFact result = subject.findOne("Australia", "Oceania");

        assertThat(result)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision(""))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testFindOneWithSubdivision() throws Exception {
        final LocationFact result = subject.findOne("New South Wales", "Australia", "Oceania");

        assertThat(result)
                .satisfies(hasCity(""))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

}
