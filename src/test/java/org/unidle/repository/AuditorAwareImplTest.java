package org.unidle.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.User;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorAwareImplTest {

    @Autowired
    private AuditorAware subject;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = new User();

        user.setEmail("email@example.com");
        user.setFirstName("first name");
        user.setLastName("last name");

        user = userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testGetCurrentAuditor() throws Exception {
        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUuid(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Object result = subject.getCurrentAuditor();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testGetCurrentAuditorWithoutAuthentication() throws Exception {
        final Object result = subject.getCurrentAuditor();

        assertThat(result).isNull();
    }

    @Test
    public void testGetCurrentAuditorWithoutAuthenticationUuid() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));

        final Object result = subject.getCurrentAuditor();

        assertThat(result).isNull();
    }

}
