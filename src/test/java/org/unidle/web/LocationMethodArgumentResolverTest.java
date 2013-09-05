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

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.service.Location;

import javax.servlet.http.Cookie;
import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasCity;
import static org.unidle.test.Conditions.hasContinent;
import static org.unidle.test.Conditions.hasCountry;
import static org.unidle.test.Conditions.hasSubdivision;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationMethodArgumentResolverTest {

    private static final Method TEST_METHOD = MethodUtils.getAccessibleMethod(TestClass.class, "testMethod", Location.class, String.class);

    private MethodParameter locationMethodParameter;

    private MockHttpServletRequest mockRequest;

    private MethodParameter stringMethodParameter;

    @Autowired
    @Qualifier("locationMethodArgumentResolver")
    private HandlerMethodArgumentResolver subject;

    @Before
    public void setUp() throws Exception {
        locationMethodParameter = new MethodParameter(TEST_METHOD, 0);
        mockRequest = new MockHttpServletRequest();
        stringMethodParameter = new MethodParameter(TEST_METHOD, 1);
    }

    @Test
    public void testResolveArgument() throws Exception {
        mockRequest.setRemoteAddr("203.27.21.6");

        final Location result = (Location) subject.resolveArgument(null, null, new ServletWebRequest(mockRequest), null);

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentWithCookieOverride() throws Exception {
        mockRequest.setRemoteAddr("140.159.2.36"); // Melbourne
        mockRequest.setCookies(new Cookie("address", "203.27.21.6")); // Sydney

        final Location result = (Location) subject.resolveArgument(null, null, new ServletWebRequest(mockRequest), null);

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentWithHeaderOverride() throws Exception {
        mockRequest.setRemoteAddr("140.159.2.36"); // Melbourne
        mockRequest.addHeader("X-Forwarded-For", "203.27.21.6"); // Sydney

        final Location result = (Location) subject.resolveArgument(null, null, new ServletWebRequest(mockRequest), null);

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testSupportsParameterForLocationArgument() throws Exception {
        final boolean result = subject.supportsParameter(locationMethodParameter);

        assertThat(result).isTrue();
    }

    @Test
    public void testSupportsParameterForNonLocationArgument() throws Exception {
        final boolean result = subject.supportsParameter(stringMethodParameter);

        assertThat(result).isFalse();
    }

    public static final class TestClass {

        @SuppressWarnings("unused")
        public void testMethod(Location location, String string) {
            // Nothing to see here
        }

    }

}
