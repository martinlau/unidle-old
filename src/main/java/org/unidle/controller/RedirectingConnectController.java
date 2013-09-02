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
import org.springframework.web.util.UriComponentsBuilder;

public class RedirectingConnectController extends ConnectController {

    public RedirectingConnectController(final ConnectionFactoryLocator connectionFactoryLocator,
                                        final ConnectionRepository connectionRepository) {

        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(final String providerId) {
        return "redirect:" + UriComponentsBuilder.newInstance()
                                                 .path("/account")
                                                 .queryParam("connected", providerId)
                                                 .build()
                                                 .encode()
                                                 .toUriString();
    }

}
