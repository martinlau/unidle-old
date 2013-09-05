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
import static org.unidle.support.RequestKeys.BUILD_TIMESTAMP;
import static org.unidle.support.RequestKeys.CURRENT_USER;
import static org.unidle.support.RequestKeys.SEGMENT_IO_API_KEY;

public class RequestKeysTest {

    @Test
    public void testGetName() throws Exception {
        assertThat(BUILD_TIMESTAMP.getName()).isEqualTo("buildTimestamp");
        assertThat(CURRENT_USER.getName()).isEqualTo("currentUser");
        assertThat(SEGMENT_IO_API_KEY.getName()).isEqualTo("segmentIoApiKey");
    }

    @Test
    public void testValueOf() throws Exception {
        assertThat(RequestKeys.valueOf("BUILD_TIMESTAMP")).isEqualTo(BUILD_TIMESTAMP);
    }

    @Test
    public void testValues() throws Exception {
        assertThat(RequestKeys.values()).containsOnly(BUILD_TIMESTAMP,
                                                      CURRENT_USER,
                                                      SEGMENT_IO_API_KEY);
    }

}
