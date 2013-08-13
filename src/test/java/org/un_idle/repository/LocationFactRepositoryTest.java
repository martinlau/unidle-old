package org.un_idle.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.un_idle.config.RootContextConfiguration;
import org.un_idle.domain.LocationFact;

import static org.fest.assertions.Assertions.assertThat;
import static org.un_idle.test.Conditions.hasCity;
import static org.un_idle.test.Conditions.hasContinent;
import static org.un_idle.test.Conditions.hasCountry;
import static org.un_idle.test.Conditions.hasSubdivision;

@ContextConfiguration(classes = RootContextConfiguration.class)
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
