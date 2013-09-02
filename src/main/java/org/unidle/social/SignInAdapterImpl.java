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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class SignInAdapterImpl implements SignInAdapter {

    public static final int LAST_LOGIN_SOURCE_COOKIE_MAX_AGE = 60 * 60 * 24 * 365 * 10;

    public static final String LAST_LOGIN_SOURCE_COOKIE_NAME = "last_login_source";

    @Override
    public String signIn(final String userId,
                         final Connection<?> connection,
                         final NativeWebRequest request) {

        final Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null);

        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);


        final Cookie cookie = new Cookie(LAST_LOGIN_SOURCE_COOKIE_NAME, connection.createData().getProviderId());
        cookie.setMaxAge(LAST_LOGIN_SOURCE_COOKIE_MAX_AGE);
        request.getNativeResponse(HttpServletResponse.class).addCookie(cookie);

        return null;
    }

}
