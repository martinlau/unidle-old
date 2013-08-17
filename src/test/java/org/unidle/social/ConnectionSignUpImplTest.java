package org.unidle.social;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.RootContextConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.repository.UserRepository;
import org.unidle.social.test.ConnectionStub;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = RootContextConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConnectionSignUpImplTest {

    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testExecute() throws Exception {
        final UserProfile userProfile = new UserProfileBuilder().setEmail("email@example.com")
                                                                .setFirstName("first name")
                                                                .setLastName("last name")
                                                                .setName("name")
                                                                .setUsername("username")
                                                                .build();
        final Connection<?> connection = new ConnectionStub(userProfile);

        final String result = connectionSignUp.execute(connection);

        final String id = userRepository.findAll()
                                      .get(0)
                                      .getId()
                                      .toString();
        assertThat(result).isEqualTo(id);
    }

}
