package org.unidle.web;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnalyticsIdInterceptor extends HandlerInterceptorAdapter {

    public static final String GOOGLE_ANALYTICS_ID = "googleAnalyticsId";

    private final String googleAnalyticsId;

    public AnalyticsIdInterceptor(final String googleAnalyticsId) {
        this.googleAnalyticsId = googleAnalyticsId;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) throws Exception {

            modelAndView.addObject(GOOGLE_ANALYTICS_ID, googleAnalyticsId);

    }

}
