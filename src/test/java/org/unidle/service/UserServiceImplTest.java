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
package org.unidle.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasEmail;
import static org.unidle.test.Conditions.hasFirstName;
import static org.unidle.test.Conditions.hasLastName;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService subject;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        final User user = new User();
        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        userRepository.save(user);
    }

    @Test
    public void testCreateUser() throws Exception {
        final User user = subject.createUser("new@example.com",
                                             "first name",
                                             "last name");

        assertThat(user)
                .satisfies(hasEmail("new@example.com"))
                .satisfies(hasFirstName("first name"))
                .satisfies(hasLastName("last name"));
    }

    @Test
    public void testExistsWithExistingEmail() throws Exception {
        final boolean result = subject.exists("email@example.com");

        assertThat(result).isTrue();
    }

    @Test
    public void testExistsWithNewEmail() throws Exception {
        final boolean result = subject.exists("new@example.com");

        assertThat(result).isFalse();
    }

}
