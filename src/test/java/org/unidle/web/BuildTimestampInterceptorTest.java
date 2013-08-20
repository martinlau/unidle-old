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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
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
    public void testPostHandleWithHandlerMethodWithCorrectBean() throws Exception {
        subject.postHandle(null, null, new HandlerMethod(this, "toString"), modelAndView);

        assertThat(modelAndView.getModel())
                .satisfies(containsKey((BUILD_TIMESTAMP)));
    }

    @Test
    public void testPostHandleWithHandlerMethodWithWrongBean() throws Exception {
        subject.postHandle(null, null, new HandlerMethod(new Object(), "toString"), modelAndView);

        assertThat(modelAndView.getModel()).isEmpty();
    }

    @Test
    public void testPostHandleWithRedirectView() throws Exception {
        modelAndView.setView(new RedirectView());

        subject.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModel()).isEmpty();
    }

    @Test
    public void testPostHandleWithRedirectViewName() throws Exception {
        modelAndView.setViewName("redirect:/");

        subject.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModel()).isEmpty();
    }

    @Test
    public void testPostHandleWithoutHandlerMethod() throws Exception {
        subject.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModel()).isEmpty();
    }

}
