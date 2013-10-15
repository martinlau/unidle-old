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
package org.unidle.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.domain.User;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorAwareImplTest {

    @Autowired
    private AuditorAware subject;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        user = userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testGetCurrentAuditor() throws Exception {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUuid(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Object result = subject.getCurrentAuditor();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testGetCurrentAuditorWithoutAuthentication() throws Exception {
        final Object result = subject.getCurrentAuditor();

        assertThat(result).isNull();
    }

    @Test
    public void testGetCurrentAuditorWithoutUuid() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));

        final Object result = subject.getCurrentAuditor();

        assertThat(result).isNull();
    }

}
