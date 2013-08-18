package org.unidle.social;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.RootConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.repository.UserRepository;
import org.unidle.social.test.ConnectionStub;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = RootConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UsersConnectionRepositoryImplTest {

    @Autowired
    private UsersConnectionRepository subject;

    private User user;

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = userRepository.save(new User());

        final UserConnection userConnection = new UserConnection();
        userConnection.setAccessToken("access token");
        userConnection.setUser(user);
        userConnection.setProviderId("twitter");
        userConnection.setProviderUserId("twitter_1");

        userConnectionRepository.save(asList(userConnection));
    }

    @Test
    public void testCreateConnectionRepository() throws Exception {
        final String userId = user.getId()
                                  .toString();

        final ConnectionRepository result = subject.createConnectionRepository(userId);

        assertThat(result).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateConnectionRepositoryWithNull() throws Exception {
        subject.createConnectionRepository(null);
    }

    @Test
    public void testFindUserIdsConnectedTo() throws Exception {
        final Set<String> result = subject.findUserIdsConnectedTo("twitter", newHashSet("twitter_1"));

        assertThat(result).containsOnly(user.getId().toString());
    }

    @Test
    public void testFindUserIdsWithConnection() throws Exception {
        final ConnectionData connectionData = new ConnectionData("twitter",
                                                                 "twitter_1",
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 null);
        final Connection<?> connection = new ConnectionStub(connectionData);

        final List<String> result = subject.findUserIdsWithConnection(connection);

        assertThat(result).containsExactly(user.getId().toString());
    }

    @Test
    public void testFindUserIdsWithConnectionWithConnectionSignup() throws Exception {
        final UserProfile userProfile = new UserProfileBuilder().setEmail("email@example.com")
                                                                .setFirstName("first name")
                                                                .setLastName("last name")
                                                                .setName("name")
                                                                .setUsername("username")
                                                                .build();
        final ConnectionData connectionData = new ConnectionData("twitter",
                                                                 "twitter_2",
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 "access token",
                                                                 null,
                                                                 null,
                                                                 null);
        final Connection<?> connection = new ConnectionStub(connectionData, userProfile);

        final List<String> result = subject.findUserIdsWithConnection(connection);

        assertThat(result).containsExactly(String.valueOf(user.getId() + 1));
    }

}
