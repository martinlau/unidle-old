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
package org.unidle.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

public class RedirectingConnectController extends ConnectController {

    public RedirectingConnectController(final ConnectionFactoryLocator connectionFactoryLocator,
                                        final ConnectionRepository connectionRepository) {

        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(final String providerId) {

        return REDIRECT_URL_PREFIX + UriComponentsBuilder.newInstance()
                                                         .path("/questions")
                                                         .build()
                                                         .encode()
                                                         .toUriString();
    }

    @Override
    protected RedirectView connectionStatusRedirect(final String providerId, final NativeWebRequest request) {

        final String url = UriComponentsBuilder.newInstance()
                                               .path("/questions")
                                               .build()
                                               .encode()
                                               .toUriString();

        return new RedirectView(url, true);
    }

}
