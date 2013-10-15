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
package org.unidle.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.unidle.config.AnalyticsConfiguration;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.unidle.test.Conditions.hasEmail;
import static org.unidle.test.Conditions.hasFirstName;
import static org.unidle.test.Conditions.hasLastName;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = AnalyticsConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
public class UpdateAccountControllerTest {

    private MockMvc subject;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        subject = webAppContextSetup(webApplicationContext).build();

        user = new User();

        user.setEmail("another@example.com");
        user.setFirstName("another first name");
        user.setLastName("another last name");

        user = userRepository.save(user);

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
    public void testAccount() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        subject.perform(get("/account/update"))
               .andExpect(status().isOk())
               .andExpect(view().name(".ajax.update-account"))
               .andExpect(model().attribute("userForm", allOf(hasProperty("email", equalTo("email@example.com")),
                                                              hasProperty("firstName", equalTo("first name")),
                                                              hasProperty("lastName", equalTo("last name")))));
    }

    @Test
    public void testAccountWithoutAuthentication() throws Exception {
        subject.perform(get("/account/update"))
               .andExpect(view().name("redirect:/signin"))
               .andExpect(model().attributeDoesNotExist("userForm"));
    }

    @Test
    public void testUpdate() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        subject.perform(post("/account/update")
                                .param("email", "new@example.com")
                                .param("firstName", "new first name")
                                .param("lastName", "new last name"))
               .andExpect(view().name(".ajax.updated-account"))
               .andExpect(model().attribute("user", allOf(hasProperty("email", equalTo("new@example.com")),
                                                          hasProperty("firstName", equalTo("new first name")),
                                                          hasProperty("lastName", equalTo("new last name")))));

        assertThat(userRepository.findOne(user.getUuid()))
                .satisfies(hasEmail("new@example.com"))
                .satisfies(hasFirstName("new first name"))
                .satisfies(hasLastName("new last name"));
    }

    @Test
    public void testUpdateWithSameEmail() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        subject.perform(post("/account/update")
                                .param("email", "email@example.com")
                                .param("firstName", "new first name")
                                .param("lastName", "new last name"))
               .andExpect(view().name(".ajax.updated-account"));

        assertThat(userRepository.findOne(user.getUuid()))
                .satisfies(hasEmail("email@example.com"))
                .satisfies(hasFirstName("new first name"))
                .satisfies(hasLastName("new last name"));
    }

    @Test
    public void testUpdateWithoutAuthentication() throws Exception {
        subject.perform(post("/account/update"))
               .andExpect(view().name("redirect:/signin"));
    }

    @Test
    public void testUpdateWithoutAuthenticationWithExistingEmail() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid(), null));

        subject.perform(post("/account/update")
                                .param("email", "another@example.com")
                                .param("firstName", "new first name")
                                .param("lastName", "new last name"))
               .andExpect(view().name(".ajax.update-account"))
               .andExpect(model().attributeHasFieldErrors("userForm", "email"));
    }

}
