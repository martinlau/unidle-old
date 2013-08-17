package org.unidle.test;

import org.fest.assertions.Condition;
import org.unidle.domain.TimeUnit;
import org.unidle.domain.User;

import java.util.Map;

import static java.lang.String.format;
import static org.springframework.test.util.ReflectionTestUtils.getField;

public class Conditions {

    public static Condition<Map<?, ?>> containsKey(final Object key) {
        return new Condition<Map<?, ?>>(format("contains key: %s", key)) {
            @Override
            public boolean matches(final Map<?, ?> value) {
                return value.containsKey(key);
            }
        };
    }

    public static Condition<Object> hasAccessToken(final String accessToken) {
        return new Condition<Object>(format("has access token: %s", accessToken)) {
            @Override
            public boolean matches(final Object value) {
                return accessToken.equals(getField(value, "accessToken"));
            }
        };
    }

    public static Condition<Object> hasCity(final String city) {
        return new Condition<Object>(format("has city: %s", city)) {
            @Override
            public boolean matches(final Object value) {
                return city.equals(getField(value, "city"));
            }
        };
    }

    public static Condition<Object> hasContinent(final String continent) {
        return new Condition<Object>(format("has continent: %s", continent)) {
            @Override
            public boolean matches(final Object value) {
                return continent.equals(getField(value, "continent"));
            }
        };
    }

    public static Condition<Object> hasCountry(final String country) {
        return new Condition<Object>(format("has country: %s", country)) {
            @Override
            public boolean matches(final Object value) {
                return country.equals(getField(value, "country"));
            }
        };
    }

    public static Condition<Object> hasDisplayName(final String displayName) {
        return new Condition<Object>(format("has display name: %s", displayName)) {
            @Override
            public boolean matches(final Object value) {
                return displayName.equals(getField(value, "displayName"));
            }
        };
    }

    public static Condition<Object> hasExpireTime(final Long expireTime) {
        return new Condition<Object>(format("has expire time: %s", expireTime)) {
            @Override
            public boolean matches(final Object value) {
                return expireTime.equals(getField(value, "expireTime"));
            }
        };
    }

    public static Condition<Object> hasImageUrl(final String imageUrl) {
        return new Condition<Object>(format("has image url: %s", imageUrl)) {
            @Override
            public boolean matches(final Object value) {
                return imageUrl.equals(getField(value, "imageUrl"));
            }
        };
    }

    public static Condition<Object> hasProfileUrl(final String profileUrl) {
        return new Condition<Object>(format("has profile url: %s", profileUrl)) {
            @Override
            public boolean matches(final Object value) {
                return profileUrl.equals(getField(value, "profileUrl"));
            }
        };
    }

    public static Condition<Object> hasProviderId(final String providerId) {
        return new Condition<Object>(format("has provider id: %s", providerId)) {
            @Override
            public boolean matches(final Object value) {
                return providerId.equals(getField(value, "providerId"));
            }
        };
    }

    public static Condition<Object> hasProviderUserId(final String providerUserId) {
        return new Condition<Object>(format("has provider user id: %s", providerUserId)) {
            @Override
            public boolean matches(final Object value) {
                return providerUserId.equals(getField(value, "providerUserId"));
            }
        };
    }

    public static Condition<Object> hasConnectionKeyProviderUserId(final String providerUserId) {
        return new Condition<Object>(format("has connection key provider user id: %s", providerUserId)) {
            @Override
            public boolean matches(final Object value) {
                return providerUserId.equals(getField(getField(value, "key"), "providerUserId"));
            }
        };
    }

    public static Condition<Object> hasConnectionKeyProviderId(final String providerId) {
        return new Condition<Object>(format("has connection key provider id: %s", providerId)) {
            @Override
            public boolean matches(final Object value) {
                return providerId.equals(getField(getField(value, "key"), "providerId"));
            }
        };
    }

    public static Condition<Object> hasRefreshToken(final String refreshToken) {
        return new Condition<Object>(format("has refresh token: %s", refreshToken)) {
            @Override
            public boolean matches(final Object value) {
                return refreshToken.equals(getField(value, "refreshToken"));
            }
        };
    }

    public static Condition<Object> hasSecret(final String secret) {
        return new Condition<Object>(format("has secret: %s", secret)) {
            @Override
            public boolean matches(final Object value) {
                return secret.equals(getField(value, "secret"));
            }
        };
    }

    public static Condition<Object> hasSource(final String source) {
        return new Condition<Object>(format("has source: %s", source)) {
            @Override
            public boolean matches(final Object value) {
                return source.equals(getField(value, "source"));
            }
        };
    }

    public static Condition<Object> hasSubdivision(final String subdivision) {
        return new Condition<Object>(format("has subdivision: %s", subdivision)) {
            @Override
            public boolean matches(final Object value) {
                return subdivision.equals(getField(value, "subdivision"));
            }
        };
    }

    public static Condition<Object> hasSummaryDuration(final Integer summaryDuration) {
        return new Condition<Object>(format("has summary duration: %s", summaryDuration)) {
            @Override
            public boolean matches(final Object value) {
                return summaryDuration.equals(getField(value, "summaryDuration"));
            }
        };
    }

    public static Condition<Object> hasSummaryDurationTimeUnit(final TimeUnit summaryDurationTimeUnit) {
        return new Condition<Object>(format("has summary duration time unit: %s", summaryDurationTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return summaryDurationTimeUnit.equals(getField(value, "summaryDurationTimeUnit"));
            }
        };
    }

    public static Condition<Object> hasTaskCode(final String taskCode) {
        return new Condition<Object>(format("has task code: %s", taskCode)) {
            @Override
            public boolean matches(final Object value) {
                return taskCode.equals(getField(value, "taskCode"));
            }
        };
    }

    public static Condition<Object> hasTaskDuration(final Integer taskDuration) {
        return new Condition<Object>(format("has task duration: %s", taskDuration)) {
            @Override
            public boolean matches(final Object value) {
                return taskDuration.equals(getField(value, "taskDuration"));
            }
        };
    }

    public static Condition<Object> hasTaskDurationTimeUnit(final TimeUnit taskDurationTimeUnit) {
        return new Condition<Object>(format("has task duration time unit: %s", taskDurationTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return taskDurationTimeUnit.equals(getField(value, "taskDurationTimeUnit"));
            }
        };
    }

    public static Condition<Object> hasTaskPeople(final Integer taskPeople) {
        return new Condition<Object>(format("has task people: %s", taskPeople)) {
            @Override
            public boolean matches(final Object value) {
                return taskPeople.equals(getField(value, "taskPeople"));
            }
        };
    }

    public static Condition<Object> hasTaskTimeUnit(final TimeUnit taskTimeUnit) {
        return new Condition<Object>(format("has task time unit: %s", taskTimeUnit)) {
            @Override
            public boolean matches(final Object value) {
                return taskTimeUnit.equals(getField(value, "taskTimeUnit"));
            }
        };
    }

    public static Condition<Object> hasUser(final User user) {
        return new Condition<Object>(format("has user: %s", user)) {
            @Override
            public boolean matches(final Object value) {
                return user.equals(getField(value, "user"));
            }
        };
    }

    public static Condition<Object> hasRank(final Integer rank) {
        return new Condition<Object>(format("has rank: %d", rank)) {
            @Override
            public boolean matches(final Object value) {
                return rank.equals(getField(value, "rank"));
            }
        };
    }


}
