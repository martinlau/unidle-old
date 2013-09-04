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
package org.unidle.support;

import org.junit.Test;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;
import org.unidle.social.test.ConnectionFactoryLocatorStub;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.security.crypto.encrypt.Encryptors.noOpText;
import static org.unidle.support.Functions.toConnection;
import static org.unidle.support.Functions.toConnectionData;
import static org.unidle.support.Functions.toUserConnection;
import static org.unidle.test.Conditions.hasAccessToken;
import static org.unidle.test.Conditions.hasConnectionKeyProviderId;
import static org.unidle.test.Conditions.hasConnectionKeyProviderUserId;
import static org.unidle.test.Conditions.hasDisplayName;
import static org.unidle.test.Conditions.hasExpireTime;
import static org.unidle.test.Conditions.hasImageUrl;
import static org.unidle.test.Conditions.hasProfileUrl;
import static org.unidle.test.Conditions.hasProviderId;
import static org.unidle.test.Conditions.hasProviderUserId;
import static org.unidle.test.Conditions.hasRank;
import static org.unidle.test.Conditions.hasRefreshToken;
import static org.unidle.test.Conditions.hasSecret;
import static org.unidle.test.Conditions.hasUser;

public class FunctionsTest {

    @Test
    public void testConstructor() throws Exception {
        // XXX A dumb test case just to get method coverage > 0% for this one :S (since jacoco can't be configured to skip private constructors yet)
        new Functions();
    }

    @Test
    public void testToConnectionData() throws Exception {
        final UserConnection userConnection = new UserConnection();

        userConnection.setAccessToken("access token");
        userConnection.setDisplayName("display name");
        userConnection.setExpireTime(1234L);
        userConnection.setImageUrl("image url");
        userConnection.setProfileUrl("profile url");
        userConnection.setProviderId("provider id");
        userConnection.setProviderUserId("provider user id");
        userConnection.setRefreshToken("refresh token");
        userConnection.setSecret("secret");

        final ConnectionData result = toConnectionData(noOpText()).apply(userConnection);

        assertThat(result)
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasSecret("secret"));
    }

    @Test
    public void testToConnectionDataWithNullAccessToken() throws Exception {
        final UserConnection userConnection = new UserConnection();

        userConnection.setAccessToken(null);
        userConnection.setDisplayName("display name");
        userConnection.setExpireTime(1234L);
        userConnection.setImageUrl("image url");
        userConnection.setProfileUrl("profile url");
        userConnection.setProviderId("provider id");
        userConnection.setProviderUserId("provider user id");
        userConnection.setRefreshToken("refresh token");
        userConnection.setSecret("secret");

        final ConnectionData result = toConnectionData(noOpText()).apply(userConnection);

        assertThat(result)
                .satisfies(hasAccessToken(null))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasSecret("secret"));
    }

    @Test
    public void testToConnectionDataWithNull() throws Exception {
        final ConnectionData result = toConnectionData(noOpText()).apply(null);

        assertThat(result).isNull();
    }

    @Test
    public void testToConnectionWithConnectionFactoryLocator() throws Exception {
        final ConnectionFactoryLocator connectionFactoryLocator = new ConnectionFactoryLocatorStub();

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final Connection<?> result = toConnection(connectionFactoryLocator).apply(connectionData);

        assertThat(result)
                .satisfies(hasConnectionKeyProviderId("provider id"))
                .satisfies(hasConnectionKeyProviderUserId("provider user id"));

        assertThat(result.createData())
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasSecret("secret"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasExpireTime(1234L));
    }

    @Test
    public void testToConnectionWithConnectionFactoryLocatorTextEncryptor() throws Exception {
        final ConnectionFactoryLocator connectionFactoryLocator = new ConnectionFactoryLocatorStub();

        final UserConnection userConnection = new UserConnection();

        userConnection.setAccessToken("access token");
        userConnection.setDisplayName("display name");
        userConnection.setExpireTime(1234L);
        userConnection.setImageUrl("image url");
        userConnection.setProfileUrl("profile url");
        userConnection.setProviderId("provider id");
        userConnection.setProviderUserId("provider user id");
        userConnection.setRefreshToken("refresh token");
        userConnection.setSecret("secret");

        final Connection<?> result = toConnection(connectionFactoryLocator, noOpText()).apply(userConnection);

        assertThat(result)
                .satisfies(hasConnectionKeyProviderId("provider id"))
                .satisfies(hasConnectionKeyProviderUserId("provider user id"));

        assertThat(result.createData())
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasSecret("secret"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasExpireTime(1234L));
    }

    @Test
    public void testToConnectionWithConnectionFactoryLocatorWithNull() throws Exception {
        final Connection<?> result = toConnection(null).apply(null);

        assertThat(result).isNull();
    }

    @Test
    public void testToUserConnectionWithNull() throws Exception {
        final UserConnection result = toUserConnection(null,
                                                       noOpText(),
                                                       null,
                                                       null).apply(null);

        assertThat(result).isNull();
    }

    @Test
    public void testToUserConnectionWithTextEncryptorIntegerUser() throws Exception {
        final User user = new User();

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final UserConnection result = toUserConnection(noOpText(),
                                                       1234,
                                                       user).apply(connectionData);

        assertThat(result)
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasSecret("secret"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasUser(user))
                .satisfies(hasRank(1234));
    }

    @Test
    public void testToUserConnectionWithTextEncryptorIntegerUserAndNullAccessTokenSecretRefreshToken() throws Exception {
        final User user = new User();

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 null,
                                                                 null,
                                                                 null,
                                                                 1234L);

        final UserConnection result = toUserConnection(noOpText(),
                                                       1234,
                                                       user).apply(connectionData);

        assertThat(result)
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken(null))
                .satisfies(hasSecret(null))
                .satisfies(hasRefreshToken(null))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasUser(user))
                .satisfies(hasRank(1234));
    }

    @Test
    public void testToUserConnectionWithUserConnectionTextEncryptorIntegerUser() throws Exception {

        final UserConnection userConnection = new UserConnection();

        final User user = new User();

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final UserConnection result = toUserConnection(userConnection,
                                                       noOpText(),
                                                       1234,
                                                       user).apply(connectionData);

        assertThat(result)
                .isSameAs(userConnection)
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasSecret("secret"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasUser(user))
                .satisfies(hasRank(1234));
    }

    @Test
    public void testToUserConnectionWithUserConnectionTextEncryptorUser() throws Exception {
        final UserConnection userConnection = new UserConnection();

        final User user = new User();

        final ConnectionData connectionData = new ConnectionData("provider id",
                                                                 "provider user id",
                                                                 "display name",
                                                                 "profile url",
                                                                 "image url",
                                                                 "access token",
                                                                 "secret",
                                                                 "refresh token",
                                                                 1234L);

        final UserConnection result = toUserConnection(userConnection,
                                                       noOpText(),
                                                       user).apply(connectionData);

        assertThat(result)
                .isSameAs(userConnection)
                .satisfies(hasProviderId("provider id"))
                .satisfies(hasProviderUserId("provider user id"))
                .satisfies(hasDisplayName("display name"))
                .satisfies(hasProfileUrl("profile url"))
                .satisfies(hasImageUrl("image url"))
                .satisfies(hasAccessToken("access token"))
                .satisfies(hasSecret("secret"))
                .satisfies(hasRefreshToken("refresh token"))
                .satisfies(hasExpireTime(1234L))
                .satisfies(hasUser(user));
    }

}
