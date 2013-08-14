package org.unidle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuildTimestampInterceptor extends HandlerInterceptorAdapter {

    private static final String TIMESTAMP = "buildTimestamp";

    private final String timestamp;

    public BuildTimestampInterceptor(final String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        modelAndView.addObject(TIMESTAMP, timestamp);
    }

}
