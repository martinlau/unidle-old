package org.unidle.config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

import static javax.servlet.SessionTrackingMode.COOKIE;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{CacheConfiguration.class,
                              DataConfiguration.class,
                              I18NConfiguration.class,
                              ServiceConfiguration.class,
                              WroConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfiguration.class,
                              SocialConfiguration.class};
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        servletContext.setSessionTrackingModes(EnumSet.of(COOKIE));

        servletContext.addFilter("openEntityManagerInView", OpenEntityManagerInViewFilter.class)
                      .addMappingForUrlPatterns(null, false, "/*");

        servletContext.addFilter("springSecurityFilterChain", SecurityContextPersistenceFilter.class)
                      .addMappingForUrlPatterns(null, false, "/*");

        final DelegatingFilterProxy wroFilter = servletContext.createFilter(DelegatingFilterProxy.class);
        wroFilter.setTargetFilterLifecycle(true);
        wroFilter.setBeanName("wroFilter    ");

        servletContext.addFilter("wroFilter", wroFilter)
                      .addMappingForUrlPatterns(null, false, "/resources/*");

    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
