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
package org.unidle.domain;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class UserConnectionTest {

    private UserConnection subject;

    @Before
    public void setUp() throws Exception {
        subject = new UserConnection();
    }

    @Test
    public void testGetAccessToken() throws Exception {
        setField(subject, "accessToken", "access token");

        final String result = subject.getAccessToken();

        assertThat(result).isEqualTo("access token");
    }

    @Test
    public void testGetDisplayName() throws Exception {
        setField(subject, "displayName", "display name");

        final String result = subject.getDisplayName();

        assertThat(result).isEqualTo("display name");
    }

    @Test
    public void testGetExpireTime() throws Exception {
        setField(subject, "expireTime", 1234L);

        final Long result = subject.getExpireTime();

        assertThat(result).isEqualTo(1234L);
    }

    @Test
    public void testGetImageUrl() throws Exception {
        setField(subject, "imageUrl", "image url");

        final String result = subject.getImageUrl();

        assertThat(result).isEqualTo("image url");
    }

    @Test
    public void testGetProfileUrl() throws Exception {
        setField(subject, "profileUrl", "profile url");

        final String result = subject.getProfileUrl();

        assertThat(result).isEqualTo("profile url");
    }

    @Test
    public void testGetProviderId() throws Exception {
        setField(subject, "providerId", "provider id");

        final String result = subject.getProviderId();

        assertThat(result).isEqualTo("provider id");
    }

    @Test
    public void testGetProviderUserId() throws Exception {
        setField(subject, "providerUserId", "provider user id");

        final String result = subject.getProviderUserId();

        assertThat(result).isEqualTo("provider user id");
    }

    @Test
    public void testGetRank() throws Exception {
        setField(subject, "rank", 1234);

        final Integer result = subject.getRank();

        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testGetRefreshToken() throws Exception {
        setField(subject, "refreshToken", "refresh token");

        final String result = subject.getRefreshToken();

        assertThat(result).isEqualTo("refresh token");
    }

    @Test
    public void testGetSecret() throws Exception {
        setField(subject, "secret", "secret");

        final String result = subject.getSecret();

        assertThat(result).isEqualTo("secret");
    }

    @Test
    public void testGetUser() throws Exception {
        final User user = new User();
        setField(subject, "user", user);

        final User result = subject.getUser();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testSetAccessToken() throws Exception {
        subject.setAccessToken("access token");

        final Object result = getField(subject, "accessToken");
        assertThat(result).isEqualTo("access token");
    }

    @Test
    public void testSetDisplayName() throws Exception {
        subject.setDisplayName("display name");

        final Object result = getField(subject, "displayName");
        assertThat(result).isEqualTo("display name");
    }

    @Test
    public void testSetExpireTime() throws Exception {
        subject.setExpireTime(1234L);

        final Object result = getField(subject, "expireTime");
        assertThat(result).isEqualTo(1234L);
    }

    @Test
    public void testSetImageUrl() throws Exception {
        subject.setImageUrl("image url");

        final Object result = getField(subject, "imageUrl");
        assertThat(result).isEqualTo("image url");
    }

    @Test
    public void testSetProfileUrl() throws Exception {
        subject.setProfileUrl("profile url");

        final Object result = getField(subject, "profileUrl");
        assertThat(result).isEqualTo("profile url");
    }

    @Test
    public void testSetProviderId() throws Exception {
        subject.setProviderId("provider id");

        final Object result = getField(subject, "providerId");
        assertThat(result).isEqualTo("provider id");
    }

    @Test
    public void testSetProviderUserId() throws Exception {
        subject.setProviderUserId("provider user id");

        final Object result = getField(subject, "providerUserId");
        assertThat(result).isEqualTo("provider user id");
    }

    @Test
    public void testSetRank() throws Exception {
        subject.setRank(1234);

        final Object result = getField(subject, "rank");
        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testSetRefreshToken() throws Exception {
        subject.setRefreshToken("refresh token");

        final Object result = getField(subject, "refreshToken");
        assertThat(result).isEqualTo("refresh token");
    }

    @Test
    public void testSetSecret() throws Exception {
        subject.setSecret("secret");

        final Object result = getField(subject, "secret");
        assertThat(result).isEqualTo("secret");
    }

    @Test
    public void testSetUser() throws Exception {
        final User user = new User();

        subject.setUser(user);

        final Object result = getField(subject, "user");
        assertThat(result).isEqualTo(user);
    }

}
