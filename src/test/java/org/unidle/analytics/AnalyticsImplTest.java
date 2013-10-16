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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.analytics.AnalyticsEvent.CONNECT;

public class AnalyticsImplTest {

    private String oldHost;

    private AnalyticsImpl subject;

    @Before
    public void setUp() throws Exception {
        subject = new AnalyticsImpl("test key");

        oldHost = com.github.segmentio.Analytics.getDefaultClient().getOptions().getHost();
        com.github.segmentio.Analytics.getDefaultClient().getOptions().setHost("http://127.0.0.1");
        com.github.segmentio.Analytics.getStatistics().clear();
    }

    @After
    public void tearDown() throws Exception {
        com.github.segmentio.Analytics.getStatistics().clear();
        com.github.segmentio.Analytics.getDefaultClient().getOptions().setHost(oldHost);
    }

    @Test
    public void testIdentify() throws Exception {
        subject.identify(randomUUID(), "name", "value");

        assertThat(com.github.segmentio.Analytics.getStatistics().getInserted().getCount()).isEqualTo(1);
    }

    @Test
    public void testTrack() throws Exception {
        subject.track(randomUUID(), CONNECT, "name", "value");

        assertThat(com.github.segmentio.Analytics.getStatistics().getInserted().getCount()).isEqualTo(1);
    }

}
