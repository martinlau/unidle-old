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
package org.unidle.social;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.AnalyticsConfiguration;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
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

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = AnalyticsConfiguration.class),
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
        user = new User();
        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        userRepository.save(user);

        final UserConnection userConnection = new UserConnection();
        userConnection.setAccessToken("access token");
        userConnection.setUser(this.user);
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

}
