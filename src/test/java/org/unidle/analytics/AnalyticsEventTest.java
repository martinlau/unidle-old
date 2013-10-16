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
package org.unidle.analytics;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.analytics.AnalyticsEvent.ADD_A_TAG;
import static org.unidle.analytics.AnalyticsEvent.ASK_A_QUESTION;
import static org.unidle.analytics.AnalyticsEvent.ATTACH_A_FILE;
import static org.unidle.analytics.AnalyticsEvent.CONNECT;
import static org.unidle.analytics.AnalyticsEvent.SIGN_UP;

public class AnalyticsEventTest {

    @Test
    public void testGetName() throws Exception {
        assertThat(CONNECT.getName()).isEqualTo("Connect an Account");
    }

    @Test
    public void testValueOf() throws Exception {
        assertThat(AnalyticsEvent.valueOf("CONNECT")).isEqualTo(CONNECT);
    }

    @Test
    public void testValues() throws Exception {
        assertThat(AnalyticsEvent.values()).containsOnly(ADD_A_TAG,
                                                         ASK_A_QUESTION,
                                                         ATTACH_A_FILE,
                                                         CONNECT,
                                                         SIGN_UP);
    }

}
