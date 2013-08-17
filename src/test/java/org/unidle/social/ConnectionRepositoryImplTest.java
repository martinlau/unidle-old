package org.unidle.social;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.unidle.config.RootContextConfiguration;
import org.unidle.config.SocialConfiguration;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.repository.UserRepository;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.unidle.test.Conditions.containsKey;
import static org.unidle.test.Conditions.hasProviderId;
import static org.unidle.test.Conditions.hasProviderUserId;

@ContextHierarchy({@ContextConfiguration(classes = RootContextConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConnectionRepositoryImplTest {

    @Autowired
    private ConnectionRepositoryFactory connectionRepositoryFactory;

    private ConnectionRepository subject;

    private User user;

    private UserConnection userConnection1;

    private UserConnection userConnection2;

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = userRepository.save(new User());

        userConnection1 = new UserConnection();
        userConnection1.setAccessToken("access token 1");
        userConnection1.setProviderId("twitter");
        userConnection1.setProviderUserId("provider user id 1");
        userConnection1.setRank(1);
        userConnection1.setUser(user);
        userConnectionRepository.save(userConnection1);

        userConnection2 = new UserConnection();
        userConnection2.setAccessToken("access token 2");
        userConnection2.setProviderId("twitter");
        userConnection2.setProviderUserId("provider user id 2");
        userConnection2.setRank(2);
        userConnection2.setUser(user);
        userConnectionRepository.save(userConnection2);

        subject = connectionRepositoryFactory.getConnectionRepository(user.getId().toString());
    }

//    @Test
//    public void testAddConnection() throws Exception {
//
//        subject.addConnection(connection);
//    }

    @Test
    public void testFindAllConnections() throws Exception {
        final MultiValueMap<String, Connection<?>> result = subject.findAllConnections();

        assertThat(result)
                .hasSize(1)
                .satisfies(containsKey("twitter"));
    }

//    @Test
//    public void testFindConnectionsToUsers() throws Exception {
//
//        subject.findConnectionsToUsers(providerUserIds);
//    }

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

//    @Test
//    public void testGetConnectionWithConnectionKey() throws Exception {
//
//        subject.getConnection(connectionKey);
//    }

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

//    @Test
//    public void testRemoveConnection() throws Exception {
//
//        subject.removeConnection(connection);
//    }

    @Test
    public void testRemoveConnections() throws Exception {
        subject.removeConnections("twitter");

        assertThat(userConnectionRepository.count()).isZero();
    }

//    @Test
//    public void testUpdateConnection() throws Exception {
//
//        subject.updateConnection(connection);
//    }

}
