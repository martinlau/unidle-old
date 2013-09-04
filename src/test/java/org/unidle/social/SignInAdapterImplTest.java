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
package org.unidle.social;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.ServletWebRequest;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.social.test.ConnectionStub;
import org.unidle.support.CookieKeys;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.support.CookieKeys.LAST_LOGIN_SOURCE;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
public class SignInAdapterImplTest {

    @Autowired
    private SignInAdapter subject;

    @Before
    public void setUp() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testSignIn() throws Exception {
        final ConnectionStub<Object> connection = new ConnectionStub<>(new ConnectionData("provider id",
                                                                                          "provider user id",
                                                                                          "display name",
                                                                                          "profile url",
                                                                                          "image url",
                                                                                          "access token",
                                                                                          "secret",
                                                                                          "refresh token",
                                                                                          1234L));

        final MockHttpServletResponse response = new MockHttpServletResponse();

        subject.signIn("userId", connection, new ServletWebRequest(new MockHttpServletRequest(), response));

        final Object principal = SecurityContextHolder.getContext()
                                                      .getAuthentication()
                                                      .getPrincipal();

        assertThat(principal).isEqualTo("userId");
        assertThat(response.getCookie(LAST_LOGIN_SOURCE.getName()).getValue()).isEqualTo("provider id");
    }

}
