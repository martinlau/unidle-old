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
import org.unidle.domain.LocationFact;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.domain.LocationFact.TimeUnit.DAY;
import static org.unidle.domain.LocationFact.TimeUnit.MINUTE;
import static org.unidle.domain.LocationFact.TimeUnit.YEAR;
import static org.unidle.test.Conditions.hasSource;
import static org.unidle.test.Conditions.hasSummaryDuration;
import static org.unidle.test.Conditions.hasSummaryDurationTimeUnit;
import static org.unidle.test.Conditions.hasTaskCode;
import static org.unidle.test.Conditions.hasTaskDuration;
import static org.unidle.test.Conditions.hasTaskDurationTimeUnit;
import static org.unidle.test.Conditions.hasTaskPeople;
import static org.unidle.test.Conditions.hasTaskTimeUnit;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationFactServiceImplTest {

    @Autowired
    private LocationFactService subject;

    @Test
    public void testFindBestFactWithCity() throws Exception {
        final LocationFact result = subject.findBestFact("Sydney", "New South Wales", "Australia", "Oceania");

        assertThat(result)
                .satisfies(hasTaskTimeUnit(DAY))
                .satisfies(hasTaskPeople(800000))
                .satisfies(hasTaskDuration(75))
                .satisfies(hasTaskDurationTimeUnit(MINUTE))
                .satisfies(hasTaskCode("common.task.public-transport"))
                .satisfies(hasSummaryDuration(100))
                .satisfies(hasSummaryDurationTimeUnit(YEAR))
                .satisfies(hasSource("Bureau of Transport Statistics, Transport for NSW"));
    }

    @Test
    public void testFindBestFactWithContinent() throws Exception {
        final LocationFact result = subject.findBestFact("Unknown", "Unknown", "Unknown", "Oceania");

        assertThat(result)
                .satisfies(hasTaskTimeUnit(DAY))
                .satisfies(hasTaskPeople(800000))
                .satisfies(hasTaskDuration(75))
                .satisfies(hasTaskDurationTimeUnit(MINUTE))
                .satisfies(hasTaskCode("common.task.public-transport"))
                .satisfies(hasSummaryDuration(100))
                .satisfies(hasSummaryDurationTimeUnit(YEAR))
                .satisfies(hasSource("Bureau of Transport Statistics, Transport for NSW"));
    }

    @Test
    public void testFindBestFactWithCountry() throws Exception {
        final LocationFact result = subject.findBestFact("Unknown", "Unknown", "Australia", "Oceania");

        assertThat(result)
                .satisfies(hasTaskTimeUnit(DAY))
                .satisfies(hasTaskPeople(800000))
                .satisfies(hasTaskDuration(75))
                .satisfies(hasTaskDurationTimeUnit(MINUTE))
                .satisfies(hasTaskCode("common.task.public-transport"))
                .satisfies(hasSummaryDuration(100))
                .satisfies(hasSummaryDurationTimeUnit(YEAR))
                .satisfies(hasSource("Bureau of Transport Statistics, Transport for NSW"));
    }

    @Test
    public void testFindBestFactWithNothing() throws Exception {
        final LocationFact result = subject.findBestFact("Unknown", "Unknown", "Unknown", "Unknown");

        assertThat(result)
                .satisfies(hasTaskTimeUnit(DAY))
                .satisfies(hasTaskPeople(1))
                .satisfies(hasTaskDuration(1))
                .satisfies(hasTaskDurationTimeUnit(MINUTE))
                .satisfies(hasTaskCode("common.task.unknown"))
                .satisfies(hasSummaryDuration(1))
                .satisfies(hasSummaryDurationTimeUnit(YEAR))
                .satisfies(hasSource("Martin's Imagination"));
    }

    @Test
    public void testFindBestFactWithSubdivision() throws Exception {
        final LocationFact result = subject.findBestFact("Unknown", "New South Wales", "Australia", "Oceania");

        assertThat(result)
                .satisfies(hasTaskTimeUnit(DAY))
                .satisfies(hasTaskPeople(800000))
                .satisfies(hasTaskDuration(75))
                .satisfies(hasTaskDurationTimeUnit(MINUTE))
                .satisfies(hasTaskCode("common.task.public-transport"))
                .satisfies(hasSummaryDuration(100))
                .satisfies(hasSummaryDurationTimeUnit(YEAR))
                .satisfies(hasSource("Bureau of Transport Statistics, Transport for NSW"));
    }

}
