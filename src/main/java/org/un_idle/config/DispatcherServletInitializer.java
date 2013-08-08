package org.un_idle.config;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

import static javax.servlet.SessionTrackingMode.COOKIE;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{TilesConfiguration.class,
                              MessagesConfiguration.class,
                              WroConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfiguration.class};
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        servletContext.setSessionTrackingModes(EnumSet.of(COOKIE));

        final DelegatingFilterProxy wroFilter = servletContext.createFilter(DelegatingFilterProxy.class);
        wroFilter.setTargetFilterLifecycle(true);

        servletContext.addFilter("wroFilter", wroFilter)
                      .addMappingForUrlPatterns(null, false, "/resources/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
