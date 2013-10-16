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
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.unidle.analytics.config.AnalyticsTestConfiguration;
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

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.unidle.test.Conditions.containsKey;
import static org.unidle.test.Conditions.hasConnectionKeyProviderId;
import static org.unidle.test.Conditions.hasConnectionKeyProviderUserId;
import static org.unidle.test.Conditions.hasProviderId;
import static org.unidle.test.Conditions.hasProviderUserId;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = AnalyticsTestConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConnectionRepositoryImplTest {

    @Autowired
    private ConnectionRepositoryFactory connectionRepositoryFactory;

    private ConnectionRepository subject;

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

        final UserConnection userConnection1 = new UserConnection();
        userConnection1.setAccessToken("access token 1");
        userConnection1.setProviderId("twitter");
        userConnection1.setProviderUserId("provider user id 1");
        userConnection1.setRank(1);
        userConnection1.setUser(user);

        userConnectionRepository.save(userConnection1);

        final UserConnection userConnection2 = new UserConnection();
        userConnection2.setAccessToken("access token 2");
        userConnection2.setProviderId("twitter");
        userConnection2.setProviderUserId("provider user id 2");
        userConnection2.setRank(2);
        userConnection2.setUser(user);

        userConnectionRepository.save(userConnection2);

        subject = connectionRepositoryFactory.getConnectionRepository(user.getId().toString());
    }

    @Test
    public void testAddConnection() throws Exception {
        final ConnectionData connectionData = new ConnectionData("twitter",
                                                                 "provider user id 3",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);
        final Connection<?> connection = new ConnectionStub<>(connectionData);

        subject.addConnection(connection);

        assertThat(userConnectionRepository.findAll(user, "twitter")).hasSize(3);
    }

    @Test(expected = DuplicateConnectionException.class)
    public void testAddConnectionWithDuplicate() throws Exception {
        final ConnectionData connectionData = new ConnectionData("twitter",
                                                                 "provider user id 3",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);
        final Connection<?> connection = new ConnectionStub<>(connectionData);

        subject.addConnection(connection);
        subject.addConnection(connection);
    }

    @Test
    public void testFindAllConnections() throws Exception {
        final MultiValueMap<String, Connection<?>> result = subject.findAllConnections();

        assertThat(result)
                .hasSize(1)
                .satisfies(containsKey("twitter"));
    }

    @Test
    public void testFindConnectionsToUsers() throws Exception {
        final MultiValueMap<String, String> providerUserIds = new LinkedMultiValueMap<>();
        providerUserIds.add("twitter", "provider user id 1");
        providerUserIds.add("twitter", "provider user id 2");
        providerUserIds.add("twitter", "provider user id 3");
        providerUserIds.add("facebook", "provider user id 4");

        final MultiValueMap<String, Connection<?>> result = subject.findConnectionsToUsers(providerUserIds);

        assertThat(result)
                .hasSize(2)
                .satisfies(containsKey("twitter"))
                .satisfies(containsKey("facebook"));
        assertThat(result.get("twitter")).hasSize(3);
        assertThat(result.get("twitter").get(0)).isNotNull();
        assertThat(result.get("twitter").get(1)).isNotNull();
        assertThat(result.get("twitter").get(2)).isNull();
        assertThat(result.get("facebook")).hasSize(1);
        assertThat(result.get("facebook").get(0)).isNull();
    }

    @Test
    public void testFindConnectionsWithClass() throws Exception {
        final List<Connection<Twitter>> result = subject.findConnections(Twitter.class);

        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindConnectionsWithString() throws Exception {
        final List<Connection<?>> result = subject.findConnections("twitter");

        assertThat(result).hasSize(2);
    }

    @Test
    public void testFindPrimaryConnection() throws Exception {
        final Connection<Twitter> result = subject.findPrimaryConnection(Twitter.class);

        assertThat(result.createData())
                .satisfies(hasProviderId("twitter"))
                .satisfies(hasProviderUserId("provider user id 1"));
    }

    @Test
    public void testFindPrimaryConnectionWithNoConnection() throws Exception {
        final Connection<Facebook> result = subject.findPrimaryConnection(Facebook.class);

        assertThat(result).isNull();
    }

    @Test
    public void testGetConnectionWithClassString() throws Exception {
        final Connection<Twitter> result = subject.getConnection(Twitter.class, "provider user id 1");

        assertThat(result.createData())
                .satisfies(hasProviderId("twitter"))
                .satisfies(hasProviderUserId("provider user id 1"));
    }

    @Test
    public void testGetConnectionWithConnectionKey() throws Exception {
        final ConnectionKey connectionKey = new ConnectionKey("twitter",
                                                              "provider user id 1");

        final Connection<?> result = subject.getConnection(connectionKey);

        assertThat(result)
                .satisfies(hasConnectionKeyProviderId("twitter"))
                .satisfies(hasConnectionKeyProviderUserId("provider user id 1"));
    }

    @Test(expected = NoSuchConnectionException.class)
    public void testGetConnectionWithConnectionKeyWithUnknownConnection() throws Exception {

        final ConnectionKey connectionKey = new ConnectionKey("twitter",
                                                              "unknown provider user id");
        subject.getConnection(connectionKey);
    }

    @Test
    public void testGetPrimaryConnection() throws Exception {
        final Connection<Twitter> result = subject.getPrimaryConnection(Twitter.class);

        assertThat(result.createData())
                .satisfies(hasProviderId("twitter"))
                .satisfies(hasProviderUserId("provider user id 1"));
    }

    @Test(expected = NotConnectedException.class)
    public void testGetPrimaryConnectionWithNoConnection() throws Exception {
        subject.getPrimaryConnection(Facebook.class);
    }

    @Test
    public void testRemoveConnection() throws Exception {
        final Connection<Twitter> connection = subject.findPrimaryConnection(Twitter.class);

        subject.removeConnection(connection.getKey());

        assertThat(userConnectionRepository.findAll(user)).hasSize(1);
    }

    @Test
    public void testRemoveConnections() throws Exception {
        subject.removeConnections("twitter");

        assertThat(userConnectionRepository.count()).isZero();
    }

    @Test
    public void testUpdateConnection() throws Exception {
        final Connection<Twitter> connection = subject.getPrimaryConnection(Twitter.class);
        setField(connection, "accessToken", "new access token");
        setField(connection, "secret", "new secret");

        subject.updateConnection(connection);

        assertThat(userConnectionRepository.findPrimaryConnection(user, "twitter").getRevision()).isEqualTo(1);
    }

}
