package org.unidle.social;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unidle.config.RootConfiguration;
import org.unidle.config.SocialConfiguration;

import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = RootConfiguration.class),
                   @ContextConfiguration(classes = SocialConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
public class SignInAdapterImplTest {

    @Autowired
    private SignInAdapter subject;

    @Before
    public void setUp() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testSignIn() throws Exception {
        subject.signIn("userId", null, null);

        final Object principal = SecurityContextHolder.getContext()
                                                      .getAuthentication()
                                                      .getPrincipal();

        assertThat(principal).isEqualTo("userId");
    }

}
