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

import org.junit.Test;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.support.CookieKeys.LAST_LOGIN_SOURCE;

public class CookieKeysTest {

    @Test
    public void testGetMaxAge() throws Exception {
        assertThat(LAST_LOGIN_SOURCE.getMaxAge()).isEqualTo(28);
    }

    @Test
    public void testGetMaxAgeAs() throws Exception {
        assertThat(LAST_LOGIN_SOURCE.getMaxAgeAs(SECONDS)).isEqualTo(2_419_200);
    }

    @Test
    public void testGetName() throws Exception {
        assertThat(LAST_LOGIN_SOURCE.getName()).isEqualTo("last_login_source");
    }

    @Test
    public void testGetTimeUnit() throws Exception {
        assertThat(LAST_LOGIN_SOURCE.getTimeUnit()).isEqualTo(DAYS);
    }

    @Test
    public void testValues() throws Exception {
        assertThat(CookieKeys.values()).containsOnly(LAST_LOGIN_SOURCE);
    }

    @Test
    public void testValueOf() throws Exception {
        assertThat(CookieKeys.valueOf("LAST_LOGIN_SOURCE")).isEqualTo(LAST_LOGIN_SOURCE);

    }
}
