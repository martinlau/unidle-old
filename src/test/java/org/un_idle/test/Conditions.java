package org.un_idle.test;

import org.fest.assertions.Condition;
import org.un_idle.domain.LocationFact;
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

    public static Condition<Object> hasFact(final String fact) {
        return new Condition<Object>(format("has fact: %s", fact)) {
            @Override
            public boolean matches(final Object value) {
                return fact.equals(((LocationFact) value).getFact());
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

    public static Condition<Object> hasSummary(final String summary) {
        return new Condition<Object>(format("has summary: %s", summary)) {
            @Override
            public boolean matches(final Object value) {
                return summary.equals(((LocationFact) value).getSummary());
            }
        };
    }

}
