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
package org.unidle.social;

import com.github.segmentio.models.EventProperties;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;
import org.unidle.domain.User;
import org.unidle.service.UserService;

import static com.github.segmentio.Analytics.track;
import static org.unidle.support.EventKeys.CONNECT;

public class AnalyticTrackingConnectInterceptor<S> implements ConnectInterceptor<S> {

    private final UserService userService;

    public AnalyticTrackingConnectInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void preConnect(final ConnectionFactory<S> connectionFactory,
                           final MultiValueMap<String, String> parameters,
                           final WebRequest request) {

        // Does nothing

    }

    @Override
    public void postConnect(final Connection<S> connection,
                            final WebRequest request) {

        final User user = userService.currentUser();

        final ConnectionData data = connection.createData();

        track(user.getUuid().toString(),
              CONNECT.getName(),
              new EventProperties().put("display-name", data.getDisplayName())
                                   .put("provider-id", data.getProviderId())
                                   .put("provider-user-id", data.getProviderUserId()));
    }

}
