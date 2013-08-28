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
package org.unidle.domain;

import org.junit.Before;
import org.junit.Test;
import org.unidle.domain.LocationFact.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class LocationFactTest {

    private LocationFact subject;

    @Before
    public void setUp() throws Exception {
        subject = new LocationFact();
    }

    @Test
    public void testGetCity() throws Exception {
        setField(subject, "city", "city");

        final String result = subject.getCity();

        assertThat(result).isEqualTo("city");
    }

    @Test
    public void testGetContinent() throws Exception {
        setField(subject, "continent", "continent");

        final String result = subject.getContinent();

        assertThat(result).isEqualTo("continent");
    }

    @Test
    public void testGetCountry() throws Exception {
        setField(subject, "country", "country");

        final String result = subject.getCountry();

        assertThat(result).isEqualTo("country");
    }

    @Test
    public void testGetLocationPrefix() throws Exception {
        setField(subject, "locationPrefix", "location prefix");

        final String result = subject.getLocationPrefix();

        assertThat(result).isEqualTo("location prefix");
    }

    @Test
    public void testGetLocationWithCity() throws Exception {
        subject.setCity("city");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("city");
    }

    @Test
    public void testGetLocationWithContent() throws Exception {
        subject.setContinent("continent");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("continent");
    }

    @Test
    public void testGetLocationWithCountry() throws Exception {
        subject.setCountry("country");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("country");
    }

    @Test
    public void testGetLocationWithNothing() throws Exception {
        final String result = subject.getLocation();

        assertThat(result).isNull();
    }

    @Test
    public void testGetLocationWithSubdivision() throws Exception {
        subject.setSubdivision("subdivision");

        final String result = subject.getLocation();

        assertThat(result).isEqualTo("subdivision");
    }

    @Test
    public void testGetSource() throws Exception {
        setField(subject, "source", "source");

        final String result = subject.getSource();

        assertThat(result).isEqualTo("source");
    }

    @Test
    public void testGetSubdivision() throws Exception {
        setField(subject, "subdivision", "subdivision");

        final String result = subject.getSubdivision();

        assertThat(result).isEqualTo("subdivision");
    }

    @Test
    public void testGetSummaryDuration() throws Exception {
        setField(subject, "summaryDuration", 1234);

        final Integer result = subject.getSummaryDuration();

        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testGetSummaryDurationTimeUnit() throws Exception {
        setField(subject, "summaryDurationTimeUnit", TimeUnit.DAY);

        final TimeUnit result = subject.getSummaryDurationTimeUnit();

        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testGetTaskCode() throws Exception {
        setField(subject, "taskCode", "task code");

        final String result = subject.getTaskCode();

        assertThat(result).isEqualTo("task code");
    }

    @Test
    public void testGetTaskDuration() throws Exception {
        setField(subject, "taskDuration", 1234);

        final Integer result = subject.getTaskDuration();

        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testGetTaskDurationTimeUnit() throws Exception {
        setField(subject, "taskDurationTimeUnit", TimeUnit.DAY);

        final TimeUnit result = subject.getTaskDurationTimeUnit();

        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testGetTaskPeople() throws Exception {
        setField(subject, "taskPeople", 1234);

        final Integer result = subject.getTaskPeople();

        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testGetTaskTimeUnit() throws Exception {
        setField(subject, "taskTimeUnit", TimeUnit.DAY);

        final TimeUnit result = subject.getTaskTimeUnit();

        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testSetCity() throws Exception {
        subject.setCity("city");

        final Object result = getField(subject, "city");
        assertThat(result).isEqualTo("city");
    }

    @Test
    public void testSetContinent() throws Exception {
        subject.setContinent("continent");

        final Object result = getField(subject, "continent");
        assertThat(result).isEqualTo("continent");
    }

    @Test
    public void testSetCountry() throws Exception {
        subject.setCountry("country");

        final Object result = getField(subject, "country");
        assertThat(result).isEqualTo("country");
    }

    @Test
    public void testSetLocationPrefix() throws Exception {
        subject.setLocationPrefix("location prefix");

        final Object result = getField(subject, "locationPrefix");
        assertThat(result).isEqualTo("location prefix");
    }

    @Test
    public void testSetSource() throws Exception {
        subject.setSource("source");

        final Object result = getField(subject, "source");
        assertThat(result).isEqualTo("source");
    }

    @Test
    public void testSetSubdivision() throws Exception {
        subject.setSubdivision("subdivision");

        final Object result = getField(subject, "subdivision");
        assertThat(result).isEqualTo("subdivision");
    }

    @Test
    public void testSetSummaryDuration() throws Exception {
        subject.setSummaryDuration(1234);

        final Object result = getField(subject, "summaryDuration");
        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testSetSummaryDurationTimeUnit() throws Exception {
        subject.setSummaryDurationTimeUnit(TimeUnit.DAY);

        final Object result = getField(subject, "summaryDurationTimeUnit");
        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testSetTaskCode() throws Exception {
        subject.setTaskCode("task code");

        final Object result = getField(subject, "taskCode");
        assertThat(result).isEqualTo("task code");
    }

    @Test
    public void testSetTaskDuration() throws Exception {
        subject.setTaskDuration(1234);

        final Object result = getField(subject, "taskDuration");
        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testSetTaskDurationTimeUnit() throws Exception {
        subject.setTaskDurationTimeUnit(TimeUnit.DAY);

        final Object result = getField(subject, "taskDurationTimeUnit");
        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testSetTaskPeople() throws Exception {
        subject.setTaskPeople(1234);

        final Object result = getField(subject, "taskPeople");
        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testSetTaskTimeUnit() throws Exception {
        subject.setTaskTimeUnit(TimeUnit.DAY);

        final Object result = getField(subject, "taskTimeUnit");
        assertThat(result).isEqualTo(TimeUnit.DAY);
    }

    @Test
    public void testTimeUnitGetMessageKeyForDay() throws Exception {
        final String result = TimeUnit.DAY.getMessageKey();

        assertThat(result).isEqualTo("common.period.day");
    }

    @Test
    public void testTimeUnitGetMessageKeyForHour() throws Exception {
        final String result = TimeUnit.HOUR.getMessageKey();

        assertThat(result).isEqualTo("common.period.hour");
    }

    @Test
    public void testTimeUnitGetMessageKeyForMinute() throws Exception {
        final String result = TimeUnit.MINUTE.getMessageKey();

        assertThat(result).isEqualTo("common.period.minute");
    }

    @Test
    public void testTimeUnitGetMessageKeyForMonth() throws Exception {
        final String result = TimeUnit.MONTH.getMessageKey();

        assertThat(result).isEqualTo("common.period.month");
    }

    @Test
    public void testTimeUnitGetMessageKeyForSecond() throws Exception {
        final String result = TimeUnit.SECOND.getMessageKey();

        assertThat(result).isEqualTo("common.period.second");
    }

    @Test
    public void testTimeUnitGetMessageKeyForUnknown() throws Exception {
        final String result = TimeUnit.UNKNOWN.getMessageKey();

        assertThat(result).isEqualTo("common.period.unknown");
    }

    @Test
    public void testTimeUnitGetMessageKeyForWeek() throws Exception {
        final String result = TimeUnit.WEEK.getMessageKey();

        assertThat(result).isEqualTo("common.period.week");
    }

    @Test
    public void testTimeUnitGetMessageKeyForYear() throws Exception {
        final String result = TimeUnit.YEAR.getMessageKey();

        assertThat(result).isEqualTo("common.period.year");
    }

}
