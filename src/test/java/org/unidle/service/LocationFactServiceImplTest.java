package org.unidle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.config.RootContextConfiguration;
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

@ContextConfiguration(classes = RootContextConfiguration.class)
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
        final LocationFact result = subject.findBestFact("", "", "", "Oceania");

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
        final LocationFact result = subject.findBestFact("", "", "Australia", "Oceania");

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
        final LocationFact result = subject.findBestFact("", "", "", "");

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
    public void testFindBestFactWithSubdivision() throws Exception {
        final LocationFact result = subject.findBestFact("", "New South Wales", "Australia", "Oceania");

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
