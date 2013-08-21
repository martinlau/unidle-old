package org.unidle.social;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConnectionRepositoryFactoryTest {

    @Autowired
    private ConnectionRepositoryFactory subject;

    private String userId;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        final User user = new User();
        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        userId = userRepository.save(user)
                               .getId()
                               .toString();
    }

    @Test
    public void testGetConnectionRepository() throws Exception {
        final ConnectionRepository result = subject.getConnectionRepository(userId);

        assertThat(result).isNotNull();
    }

}
