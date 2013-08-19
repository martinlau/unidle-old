package org.unidle.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.RootConfiguration;
import org.unidle.domain.User;
import org.unidle.repository.UserRepository;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasEmail;
import static org.unidle.test.Conditions.hasFirstName;
import static org.unidle.test.Conditions.hasLastName;

@ContextConfiguration(classes = RootConfiguration.class)
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
