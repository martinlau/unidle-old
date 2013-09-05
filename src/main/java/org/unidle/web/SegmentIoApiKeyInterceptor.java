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

import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.unidle.support.RequestKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;
import static org.unidle.support.RequestKeys.SEGMENT_IO_API_KEY;

public class SegmentIoApiKeyInterceptor extends HandlerInterceptorAdapter {

    private final String segmentIoApiKey;

    public SegmentIoApiKeyInterceptor(final String segmentIoApiKey) {
        this.segmentIoApiKey = segmentIoApiKey;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (modelAndView.getView() instanceof RedirectView) {
            return;
        }

        if (modelAndView.getViewName() != null &&
            modelAndView.getViewName().startsWith(REDIRECT_URL_PREFIX)) {
            return;
        }

        String packageName = "";
        if (handler instanceof HandlerMethod) {
            packageName = ClassUtils.getPackageName(((HandlerMethod) handler).getBean().getClass());
        }
        if (packageName.startsWith("org.unidle")) {
            modelAndView.addObject(SEGMENT_IO_API_KEY.getName(), segmentIoApiKey);
        }
    }

}
