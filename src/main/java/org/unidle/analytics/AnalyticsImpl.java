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

import com.github.segmentio.models.EventProperties;
import com.github.segmentio.models.Traits;

import java.util.UUID;

public class AnalyticsImpl implements Analytics {

    public AnalyticsImpl(final String apiKey) {
        com.github.segmentio.Analytics.initialize(apiKey);
    }

    @Override
    public void identify(final UUID userId,
                         final Object... traits) {

        com.github.segmentio.Analytics.identify(userId.toString(), new Traits(traits));
    }

    @Override
    public void track(final UUID userId,
                      final AnalyticsEvent event,
                      final Object... properties) {

        com.github.segmentio.Analytics.track(userId.toString(),
                                             event.getName(),
                                             new EventProperties(properties));
    }

}
