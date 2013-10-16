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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.unidle.analytics.config.AnalyticsTestConfiguration;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.support.RequestKeys.CURRENT_USER;
import static org.unidle.test.Conditions.containsKey;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = AnalyticsTestConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
public class CurrentUserInterceptorTest {

    private ModelAndView modelAndView;

    @Autowired
    @Qualifier("currentUserInterceptor")
    private HandlerInterceptorAdapter subject;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        modelAndView = new ModelAndView();

        user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        user = userRepository.save(user);
    }

    @Test
    public void testPostHandleWithHandlerMethodWithCorrectBean() throws Exception {

        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        subject.postHandle(null, null, new HandlerMethod(this, "toString"), modelAndView);

        assertThat(modelAndView.getModel())
                .satisfies(containsKey((CURRENT_USER.getName())));
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
