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
package org.unidle.config;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import java.util.EnumSet;

import static javax.servlet.SessionTrackingMode.COOKIE;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final int FILE_SIZE_THRESHOLD = -1;

    private static final String LOCATION = "";

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 3L; // 3mb

    private static final long MAX_REQUEST_SIZE = 1024L * 1024L * 1L; // 1mb

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

        final DelegatingFilterProxy wroFilter = servletContext.createFilter(DelegatingFilterProxy.class);
        wroFilter.setTargetFilterLifecycle(true);
        wroFilter.setBeanName("wroFilter");

        servletContext.addFilter("wroFilter", wroFilter)
                      .addMappingForUrlPatterns(null, false, "/resources/*");

    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new MDCInsertingServletFilter(),
                new HiddenHttpMethodFilter(),
                new OpenEntityManagerInViewFilter(),
                new RequestContextFilter(),
                new SecurityContextPersistenceFilter()
        };
    }

    @Override
    protected void customizeRegistration(final Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD));
    }

}
