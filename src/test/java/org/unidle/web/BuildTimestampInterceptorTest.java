package org.unidle.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.RootConfiguration;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.containsKey;
import static org.unidle.web.BuildTimestampInterceptor.BUILD_TIMESTAMP;

@ContextHierarchy({@ContextConfiguration(classes = RootConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BuildTimestampInterceptorTest {

    private ModelAndView modelAndView;

    @Autowired
    @Qualifier("buildTimestampInterceptor")
    private HandlerInterceptorAdapter subject;

    @Before
    public void setUp() throws Exception {
        modelAndView = new ModelAndView();
    }

    @Test
    public void testPostHandleWithInvalidPackage() throws Exception {
        subject.postHandle(null, null, new Object(), modelAndView);

        assertThat(modelAndView.getModel()).isEmpty();
    }

    @Test
    public void testPostHandleWithValidPackage() throws Exception {
        subject.postHandle(null, null, this, modelAndView);

        assertThat(modelAndView.getModel())
                .satisfies(containsKey((BUILD_TIMESTAMP)));
    }

}
