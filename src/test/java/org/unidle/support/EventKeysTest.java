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

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.support.EventKeys.CONNECT;
import static org.unidle.support.EventKeys.SIGN_UP;

public class EventKeysTest {

    @Test
    public void testGetName() throws Exception {
        assertThat(CONNECT.getName()).isEqualTo("Connected an Account");
        assertThat(SIGN_UP.getName()).isEqualTo("Signed Up");
    }

    @Test
    public void testValueOf() throws Exception {
        assertThat(EventKeys.valueOf("CONNECT")).isEqualTo(CONNECT);
    }

    @Test
    public void testValues() throws Exception {
        assertThat(EventKeys.values()).containsOnly(CONNECT,
                                                    SIGN_UP);
    }

}
