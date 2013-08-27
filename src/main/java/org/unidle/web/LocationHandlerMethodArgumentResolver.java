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
package org.unidle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;
import org.unidle.service.Location;
import org.unidle.service.LocationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

@Component
public class LocationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final LocationService locationService;

    @Autowired
    public LocationHandlerMethodArgumentResolver(final LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return Location.class == methodParameter.getParameterType();
    }

    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) throws Exception {

        final HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        final Cookie cookie = WebUtils.getCookie(httpServletRequest, "address");

        final String address = cookie != null ? cookie.getValue()
                                              : hasText(nativeWebRequest.getHeader("X-Forwarded-For")) ? nativeWebRequest.getHeader("X-Forwarded-For")
                                                                                                       : httpServletRequest.getRemoteAddr();

        return locationService.locateAddress(address);
    }

}
