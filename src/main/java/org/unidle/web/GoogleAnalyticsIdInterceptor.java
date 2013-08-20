package org.unidle.web;

import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoogleAnalyticsIdInterceptor extends HandlerInterceptorAdapter {

    public static final String GOOGLE_ANALYTICS_ID = "googleAnalyticsId";

    private final String googleAnalyticsId;

    public GoogleAnalyticsIdInterceptor(final String googleAnalyticsId) {
        this.googleAnalyticsId = googleAnalyticsId;
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
            modelAndView.getViewName().startsWith("redirect:")) {
            return;
        }

        String packageName = "";
        if (handler instanceof HandlerMethod) {
            packageName = ClassUtils.getPackageName(((HandlerMethod) handler).getBean().getClass());
        }
        if (packageName.startsWith("org.unidle")) {
            modelAndView.addObject(GOOGLE_ANALYTICS_ID, googleAnalyticsId);
        }
    }

}
