package org.unidle.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.RootConfiguration;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;

import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasProviderId;
import static org.unidle.test.Conditions.hasProviderUserId;
import static org.unidle.test.Conditions.hasUser;

@ContextConfiguration(classes = RootConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserConnectionRepositoryTest {

    @Autowired
    private UserConnectionRepository subject;

    private User user1;

    private User user2;

    private UserConnection userConnection1;

    private UserConnection userConnection2;

    private UserConnection userConnection3;

    private UserConnection userConnection4;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user1 = userRepository.save(new User());

        user2 = userRepository.save(new User());

        userConnection1 = new UserConnection();
        userConnection1.setAccessToken("access token");
        userConnection1.setProviderId("provider id 1");
        userConnection1.setProviderUserId("provider user id 1");
        userConnection1.setRank(1);
        userConnection1.setUser(user1);

        subject.save(userConnection1);

        userConnection2 = new UserConnection();
        userConnection2.setAccessToken("access token");
        userConnection2.setProviderId("provider id 1");
        userConnection2.setProviderUserId("provider user id 2");
        userConnection2.setRank(2);
        userConnection2.setUser(user1);

        subject.save(userConnection2);

        userConnection3 = new UserConnection();
        userConnection3.setAccessToken("access token");
        userConnection3.setProviderId("provider id 1");
        userConnection3.setProviderUserId("provider user id 3");
        userConnection3.setRank(1);
        userConnection3.setUser(user2);

        subject.save(userConnection3);

        userConnection4 = new UserConnection();
        userConnection4.setAccessToken("access token");
        userConnection4.setProviderId("provider id 2");
        userConnection4.setProviderUserId("provider user id 4");
        userConnection4.setRank(2);
        userConnection4.setUser(user2);

        subject.save(userConnection4);
    }

    @Test
    public void testDeleteUserString() throws Exception {
        subject.delete(user1, "provider id 1");

        assertThat(subject.count()).isEqualTo(2);
    }

    @Test
    public void testDeleteUserStringString() throws Exception {
        subject.delete(user1, "provider id 1", "provider user id 1");

        assertThat(subject.count()).isEqualTo(3);
    }

    @Test
    public void testFindAllWithUser() throws Exception {
        final List<UserConnection> result = subject.findAll(user1);

        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindAllWithUserString() throws Exception {
        final List<UserConnection> result = subject.findAll(user1, "provider id 1");

        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindAllWithUserStringListOfStrings() throws Exception {
        final List<UserConnection> result = subject.findAll(user1,
                                                            "provider id 1",
                                                            asList("provider user id 1",
                                                                   "provider user id 2",
                                                                   "provider user id 3"));

        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindOne() throws Exception {
        final UserConnection result = subject.findOne(user1, "provider id 1", "provider user id 1");

        assertThat(result)
                .satisfies(hasUser(user1))
                .satisfies(hasProviderId("provider id 1"))
                .satisfies(hasProviderUserId("provider user id 1"));
    }

    @Test
    public void testFindPrimaryConnection() throws Exception {
        final UserConnection result = subject.findPrimaryConnection(user1, "provider id 1");

        assertThat(result)
                .satisfies(hasProviderId("provider id 1"))
                .satisfies(hasProviderUserId("provider user id 1"));
    }

    @Test
    public void testFindRank() throws Exception {
        final Integer result = subject.findRank(user1, "provider id 1");

        assertThat(result).isEqualTo(3);
    }

    @Test
    public void testFindUserIdsConnectedTo() throws Exception {
        final List<Long> result = subject.findUserIdsConnectedTo("provider id 1",
                                                                 newHashSet("provider user id 1",
                                                                            "provider user id 2",
                                                                            "provider user id 3",
                                                                            "provider user id 4"));

        assertThat(result).containsExactly(user1.getId(),
                                           user2.getId());

    }

    @Test
    public void testFindUserIdsWithConnection() throws Exception {
        final List<Long> result = subject.findUserIdsWithConnection("provider id 1",
                                                                    "provider user id 1");

        assertThat(result).containsExactly(user1.getId());
    }

}
