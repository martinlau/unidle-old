package org.unidle.web;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuildTimestampInterceptor extends HandlerInterceptorAdapter {

    public static final String BUILD_TIMESTAMP = "buildTimestamp";

    private final String timestamp;

    public BuildTimestampInterceptor(final String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (StringUtils.startsWithIgnoreCase(ClassUtils.getPackageName(handler.getClass()), "org.unidle")) {
            modelAndView.addObject(BUILD_TIMESTAMP, timestamp);
        }

    }

}
