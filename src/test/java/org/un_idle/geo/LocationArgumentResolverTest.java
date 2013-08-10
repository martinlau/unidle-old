package org.un_idle.geo;

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
    public void testResolveArgumentForAdelaideAustralia() throws Exception {
        mockRequest.setRemoteAddr("203.6.146.5");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Adelaide");
        assertThat(result.getSubdivision()).isEqualTo("South Australia");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForBrisbaneAustralia() throws Exception {
        mockRequest.setRemoteAddr("132.234.251.230");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Brisbane");
        assertThat(result.getSubdivision()).isEqualTo("Queensland");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForCanberraAustralia() throws Exception {
        mockRequest.setRemoteAddr("203.6.77.2");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Canberra");
        assertThat(result.getSubdivision()).isEqualTo("Australian Capital Territory");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForDarwinAustralia() throws Exception {
        mockRequest.setRemoteAddr("138.80.0.10");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Darwin");
        assertThat(result.getSubdivision()).isEqualTo("Northern Territory");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForHobartAustralia() throws Exception {
        mockRequest.setRemoteAddr("147.41.128.8");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Hobart");
        assertThat(result.getSubdivision()).isEqualTo("Tasmania");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForMelbourneAustralia() throws Exception {
        mockRequest.setRemoteAddr("140.159.2.36");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Melbourne");
        assertThat(result.getSubdivision()).isEqualTo("Victoria");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForNonLocationArgument() throws Exception {
        final Object result = subject.resolveArgument(stringMethodParameter, null);

        assertThat(result).isSameAs(WebArgumentResolver.UNRESOLVED);
    }

    @Test
    public void testResolveArgumentForPerthAustralia() throws Exception {
        mockRequest.setRemoteAddr("165.118.1.50");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Perth");
        assertThat(result.getSubdivision()).isEqualTo("Western Australia");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    @Test
    public void testResolveArgumentForSydneyAustralia() throws Exception {
        mockRequest.setRemoteAddr("203.27.21.6");

        final Location result = (Location) subject.resolveArgument(locationMethodParameter, new ServletWebRequest(mockRequest));

        assertThat(result.getCity()).isEqualTo("Sydney");
        assertThat(result.getSubdivision()).isEqualTo("New South Wales");
        assertThat(result.getCountry()).isEqualTo("Australia");
        assertThat(result.getContinent()).isEqualTo("Oceania");
    }

    public static final class TestClass {

        @SuppressWarnings("unused")
        public void testMethod(Location location, String string) {
            // Nothing to see here
        }

    }

}
