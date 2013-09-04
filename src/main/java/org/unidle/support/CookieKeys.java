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
package org.unidle.support;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;

public enum CookieKeys {

    LAST_LOGIN_SOURCE("last_login_source", 28, DAYS);

    private final int maxAge;

    private final String name;

    private final TimeUnit timeUnit;

    private CookieKeys(final String name, final int maxAge, final TimeUnit timeUnit) {
        this.name = name;
        this.maxAge = maxAge;
        this.timeUnit = timeUnit;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getMaxAgeAs(final TimeUnit asTimeUnit) {
        return (int) asTimeUnit.convert(maxAge, timeUnit);
    }

    public String getName() {
        return name;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
