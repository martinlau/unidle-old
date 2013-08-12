package org.un_idle.test;

import org.fest.assertions.Condition;
import org.un_idle.domain.LocationFact;
import org.un_idle.domain.TimeUnit;
import org.un_idle.service.Location;

import static java.lang.String.format;

public class Conditions {

    public static Condition<Object> hasCity(final String city) {
        return new Condition<Object>(format("has city: %s", city)) {
            @Override
            public boolean matches(final Object value) {
                return city.equals(((Location) value).getCity());
            }
        };
    }

    public static Condition<Object> hasContinent(final String continent) {
        return new Condition<Object>(format("has continent: %s", continent)) {
            @Override
            public boolean matches(final Object value) {
                return continent.equals(((Location) value).getContinent());
            }
        };
    }

    public static Condition<Object> hasCountry(final String country) {
        return new Condition<Object>(format("has country: %s", country)) {
            @Override
            public boolean matches(final Object value) {
                return country.equals(((Location) value).getCountry());
            }
        };
    }

    public static Condition<Object> hasSource(final String source) {
        return new Condition<Object>(format("has source: %s", source)) {
            @Override
            public boolean matches(final Object value) {
                return source.equals(((LocationFact) value).getSource());
            }
        };
    }

    public static Condition<Object> hasSubdivision(final String subdivision) {
        return new Condition<Object>(format("has subdivision: %s", subdivision)) {
            @Override
            public boolean matches(final Object value) {
                return subdivision.equals(((Location) value).getSubdivision());
            }
        };
    }

    public static Condition<Object> hasSummaryDuration(final Integer summaryDuration) {
        return new Condition<Object>(format("has summary duration: %s", summaryDuration)) {
            @Override
            public boolean matches(final Object value) {
                return summaryDuration.equals(((LocationFact) value).getSummaryDuration());
            }
        };
    }

    public static Condition<Object> hasSummaryDurationTimeUnit(final TimeUnit summaryDurationTimeUnit) {
        return new Condition<Object>(format("has summary duration time unit: %s", summaryDurationTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return summaryDurationTimeUnit.equals(((LocationFact) value).getSummaryDurationTimeUnit());
            }
        };
    }

    public static Condition<Object> hasTaskCode(final String taskCode) {
        return new Condition<Object>(format("has task code: %s", taskCode)) {
            @Override
            public boolean matches(final Object value) {
                return taskCode.equals(((LocationFact) value).getTaskCode());
            }
        };
    }

    public static Condition<Object> hasTaskDuration(final Integer taskDuration) {
        return new Condition<Object>(format("has task duration: %s", taskDuration)) {
            @Override
            public boolean matches(final Object value) {
                return taskDuration.equals(((LocationFact) value).getTaskDuration());
            }
        };
    }

    public static Condition<Object> hasTaskDurationTimeUnit(final TimeUnit taskDurationTimeUnit) {
        return new Condition<Object>(format("has task duration time unit: %s", taskDurationTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return taskDurationTimeUnit.equals(((LocationFact) value).getTaskDurationTimeUnit());
            }
        };
    }

    public static Condition<Object> hasTaskPeople(final Integer taskPeople) {
        return new Condition<Object>(format("has task people: %s", taskPeople)) {
            @Override
            public boolean matches(final Object value) {
                return taskPeople.equals(((LocationFact) value).getTaskPeople());
            }
        };
    }

    public static Condition<Object> hasTaskTimeUnit(final TimeUnit taskTimeUnit) {
        return new Condition<Object>(format("has task time unit: %s", taskTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return taskTimeUnit.equals(((LocationFact) value).getTaskTimeUnit());
            }
        };
    }

}
