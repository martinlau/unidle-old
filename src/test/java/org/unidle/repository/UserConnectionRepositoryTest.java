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
package org.unidle.repository;

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
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.hasProviderId;
import static org.unidle.test.Conditions.hasProviderUserId;
import static org.unidle.test.Conditions.hasUser;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserConnectionRepositoryTest {

    @Autowired
    private UserConnectionRepository subject;

    private User user1;

    private User user2;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setEmail("email-1@example.com");
        user1.setFirstName("first name 1");
        user1.setLastName("last name 1");

        userRepository.save(user1);

        user2 = new User();
        user2.setEmail("email-2@example.com");
        user2.setFirstName("first name 2");
        user2.setLastName("last name 2");

        user2 = userRepository.save(user2);

        final UserConnection userConnection1 = new UserConnection();
        userConnection1.setAccessToken("access token");
        userConnection1.setProviderId("provider id 1");
        userConnection1.setProviderUserId("provider user id 1");
        userConnection1.setRank(1);
        userConnection1.setUser(this.user1);

        subject.save(userConnection1);

        final UserConnection userConnection2 = new UserConnection();
        userConnection2.setAccessToken("access token");
        userConnection2.setProviderId("provider id 1");
        userConnection2.setProviderUserId("provider user id 2");
        userConnection2.setRank(2);
        userConnection2.setUser(this.user1);

        subject.save(userConnection2);

        final UserConnection userConnection3 = new UserConnection();
        userConnection3.setAccessToken("access token");
        userConnection3.setProviderId("provider id 1");
        userConnection3.setProviderUserId("provider user id 3");
        userConnection3.setRank(1);
        userConnection3.setUser(user2);

        subject.save(userConnection3);

        final UserConnection userConnection4 = new UserConnection();
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
        final List<UUID> result = subject.findUserIdsConnectedTo("provider id 1",
                                                                 newHashSet("provider user id 1",
                                                                            "provider user id 2",
                                                                            "provider user id 3",
                                                                            "provider user id 4"));

        assertThat(result).containsOnly(user1.getId(),
                                        user2.getId());

    }

    @Test
    public void testFindUserIdsWithConnection() throws Exception {
        final List<UUID> result = subject.findUserIdsWithConnection("provider id 1",
                                                                    "provider user id 1");

        assertThat(result).containsExactly(user1.getId());
    }

}
