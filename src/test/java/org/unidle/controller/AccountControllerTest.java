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
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
public class AccountControllerTest {

    private MockMvc subject;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Before
    public void setUp() throws Exception {
        subject = webAppContextSetup(webApplicationContext).build();

        user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        user = userRepository.save(user);
    }

    @Test
    public void testAccount() throws Exception {
        SecurityContextHolder.getContext()
                             .setAuthentication(new UsernamePasswordAuthenticationToken(user.getUuid().toString(), null));

        subject.perform(get("/account"))
               .andExpect(status().isOk())
               .andExpect(view().name(".account"))
               .andExpect(model().attribute("user", allOf(hasProperty("email", equalTo("email@example.com")),
                                                          hasProperty("firstName", equalTo("first name")),
                                                          hasProperty("lastName", equalTo("last name")))))
               .andExpect(model().attribute("userForm", allOf(hasProperty("email", equalTo("email@example.com")),
                                                              hasProperty("firstName", equalTo("first name")),
                                                              hasProperty("lastName", equalTo("last name")))));
    }

    @Test
    public void testAccountWithoutAuthentication() throws Exception {
        subject.perform(get("/account"))
               .andExpect(status().isOk())
               .andExpect(view().name(".account"))
               .andExpect(model().attributeDoesNotExist("user"))
               .andExpect(model().attribute("userForm", allOf(hasProperty("email", nullValue()),
                                                              hasProperty("firstName", nullValue()),
                                                              hasProperty("lastName", nullValue()))));
    }

}