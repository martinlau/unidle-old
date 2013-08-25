package org.unidle.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInAttempt;
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
import org.unidle.social.test.ConnectionFactoryLocatorStub;
import org.unidle.social.test.ConnectionFactoryStub;
import org.unidle.social.test.ConnectionStub;
import org.unidle.social.test.UsersConnectionRepositoryStub;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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
public class SignupControllerTest {

    private MockMvc subject;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        subject = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void testSignup() throws Exception {

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final UserProfile userProfile = new UserProfileBuilder().setFirstName("first name")
                                                                .setLastName("last name")
                                                                .setEmail("email@example.com")
                                                                .build();

        final Connection<?> connection = new ConnectionStub<>(connectionData, userProfile);

        final ConnectionFactory<?> connectionFactory = new ConnectionFactoryStub<>("provider id", connection);

        final ConnectionFactoryLocatorStub connectionFactoryLocator = new ConnectionFactoryLocatorStub(connectionFactory);

        final ProviderSignInAttempt providerSignInAttempt = new ProviderSignInAttempt(connection, connectionFactoryLocator, null);

        subject.perform(get("/signup")
                                .sessionAttr(ProviderSignInAttempt.class.getName(), providerSignInAttempt))
               .andExpect(view().name(".signup"))
               .andExpect(model().attribute("userForm", allOf(hasProperty("email", equalTo("email@example.com")),
                                                              hasProperty("firstName", equalTo("first name")),
                                                              hasProperty("lastName", equalTo("last name")))));
    }

    @Test
    public void testSubmit() throws Exception {

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final UserProfile userProfile = new UserProfileBuilder().setFirstName("first name")
                                                                .setLastName("last name")
                                                                .setEmail("email@example.com")
                                                                .build();

        final Connection<?> connection = new ConnectionStub<>(connectionData, userProfile);

        final ConnectionFactory<?> connectionFactory = new ConnectionFactoryStub<>("provider id", connection);

        final ConnectionFactoryLocatorStub connectionFactoryLocator = new ConnectionFactoryLocatorStub(connectionFactory);

        final UsersConnectionRepository usersConnectionRepository = new UsersConnectionRepositoryStub();

        final ProviderSignInAttempt providerSignInAttempt = new ProviderSignInAttempt(connection, connectionFactoryLocator, usersConnectionRepository);

        subject.perform(post("/signup")
                                .param("email", "email@example.com")
                                .param("firstName", "first name")
                                .param("lastName", "last name")
                                .sessionAttr(ProviderSignInAttempt.class.getName(), providerSignInAttempt))
               .andExpect(view().name("redirect:/account"))
               .andExpect(request().sessionAttribute(ProviderSignInAttempt.class.getName(), nullValue()));

        assertThat(userRepository.count()).isEqualTo(1L);
    }

    @Test
    public void testSubmitWithExistingEmail() throws Exception {
        User user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        userRepository.save(user);

        subject.perform(post("/signup")
                                .param("email", "email@example.com")
                                .param("firstName", "first name")
                                .param("lastName", "last name"))
               .andExpect(view().name(".signup"))
               .andExpect(model().attributeHasFieldErrors("userForm",
                                                          "email"));
    }

    @Test
    public void testSubmitWithValidationErrors() throws Exception {
        subject.perform(post("/signup")
                                .param("email", "")
                                .param("firstName", "")
                                .param("lastName", ""))
               .andExpect(view().name(".signup"))
               .andExpect(model().attributeHasFieldErrors("userForm",
                                                          "email", "firstName", "lastName"));
    }

}
