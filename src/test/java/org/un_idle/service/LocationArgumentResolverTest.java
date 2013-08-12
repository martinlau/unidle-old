package org.un_idle.service;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;
import static org.un_idle.test.Conditions.hasCity;
import static org.un_idle.test.Conditions.hasContinent;
import static org.un_idle.test.Conditions.hasCountry;
import static org.un_idle.test.Conditions.hasSubdivision;

@ContextConfiguration(classes = org.un_idle.config.RootContextConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LocationArgumentResolverTest {

    private static final Method TEST_METHOD = MethodUtils.getAccessibleMethod(TestClass.class, "testMethod", Location.class, String.class);

    private MethodParameter locationMethodParameter;

    private MockHttpServletRequest mockRequest;

    private MethodParameter stringMethodParameter;

    @Autowired
    @Qualifier("locationArgumentResolver")
    private WebArgumentResolver subject;

    @Before
    public void setUp() throws Exception {
        locationMethodParameter = new MethodParameter(TEST_METHOD, 0);
        mockRequest = new MockHttpServletRequest();
        stringMethodParameter = new MethodParameter(TEST_METHOD, 1);
    }

    @Test
    public void testResolveArgument() throws Exception {
        mockRequest.setRemoteAddr("203.27.21.6");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    @Test
    public void testResolveArgumentForNonLocationArgument() throws Exception {
        final Object result = subject.resolveArgument(stringMethodParameter, null);

        assertThat(result).isSameAs(WebArgumentResolver.UNRESOLVED);
    }

    @Test
    public void testResolveArgumentWithAddressOverride() throws Exception {
        mockRequest.setRemoteAddr("140.159.2.36"); // Melbourne
        mockRequest.addParameter("address", "203.27.21.6"); // Sydney

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result)
                .satisfies(hasCity("Sydney"))
                .satisfies(hasSubdivision("New South Wales"))
                .satisfies(hasCountry("Australia"))
                .satisfies(hasContinent("Oceania"));
    }

    public static final class TestClass {

        @SuppressWarnings("unused")
        public void testMethod(Location location, String string) {
            // Nothing to see here
        }

    }

}
