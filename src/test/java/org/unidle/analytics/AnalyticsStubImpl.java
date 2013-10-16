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

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

public class AnalyticsStubImpl implements Analytics {

    public MultiValueMap<UUID, Object[]> identifications = new LinkedMultiValueMap<>();

    public MultiValueMap<UUID, Pair<AnalyticsEvent, Object[]>> trackings = new LinkedMultiValueMap<>();

    @Override
    public void identify(final UUID userId,
                         final Object... traits) {

        identifications.add(userId, traits);
    }

    @Override
    public void track(final UUID userId,
                      final AnalyticsEvent event,
                      final Object... properties) {

        trackings.add(userId, Pair.of(event, properties));
    }

    public void reset() {
        identifications.clear();
        trackings.clear();
    }

}
