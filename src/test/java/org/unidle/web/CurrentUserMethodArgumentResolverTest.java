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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;
import org.unidle.service.Location;
import org.unidle.test.Conditions;

import java.lang.reflect.Method;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
public class CurrentUserMethodArgumentResolverTest {

    private static final Method TEST_METHOD = MethodUtils.getAccessibleMethod(TestClass.class, "testMethod", User.class, String.class);

    private MethodParameter stringMethodParameter;

    @Autowired
    @Qualifier("currentUserMethodArgumentResolver")
    private HandlerMethodArgumentResolver subject;

    private User user;

    private MethodParameter userMethodParameter;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userMethodParameter = new MethodParameter(TEST_METHOD, 0);
        stringMethodParameter = new MethodParameter(TEST_METHOD, 1);

        user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        user = userRepository.save(user);
    }

    @Test
    public void testResolveArgumentWithLoggedInUser() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        final User result = (User) subject.resolveArgument(null, null, null, null);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testResolveArgumentWithoutLoggedInUser() throws Exception {
        final User result = (User) subject.resolveArgument(null, null, null, null);

        assertThat(result).isNull();
    }

    @Test
    public void testSupportsParameterForLocationArgument() throws Exception {
        final boolean result = subject.supportsParameter(userMethodParameter);

        assertThat(result).isTrue();
    }

    @Test
    public void testSupportsParameterForNonLocationArgument() throws Exception {
        final boolean result = subject.supportsParameter(stringMethodParameter);

        assertThat(result).isFalse();
    }

    public static final class TestClass {

        @SuppressWarnings("unused")
        public void testMethod(User user, String string) {
            // Nothing to see here
        }

    }

}
