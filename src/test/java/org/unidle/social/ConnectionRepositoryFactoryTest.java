package org.unidle.social;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.RootContextConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = RootContextConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConnectionRepositoryFactoryTest {

    @Autowired
    private ConnectionRepositoryFactory connectionRepositoryFactory;

    private String userId;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userId = userRepository.save(new User())
                               .getId()
                               .toString();
    }

    @Test
    public void testGetConnectionRepository() throws Exception {
        final ConnectionRepositoryImpl result = connectionRepositoryFactory.getConnectionRepository(userId);

        assertThat(result).isNotNull();
    }

}