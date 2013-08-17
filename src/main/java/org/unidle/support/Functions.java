package org.unidle.support;

import com.google.common.base.Function;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;

import static com.google.common.base.Functions.compose;

public class Functions {

    public static Function<? super UserConnection, ? extends Connection<?>> toConnection(final ConnectionFactoryLocator connectionFactoryLocator,
                                                                                         final TextEncryptor textEncryptor) {

        return compose(toConnection(connectionFactoryLocator),
                       toConnectionData(textEncryptor));
    }

    public static Function<? super ConnectionData, ? extends Connection<?>> toConnection(final ConnectionFactoryLocator connectionFactoryLocator) {
        return new Function<ConnectionData, Connection<?>>() {
            @Override
            public Connection<?> apply(final ConnectionData input) {

                return input == null
                       ? null
                       : connectionFactoryLocator.getConnectionFactory(input.getProviderId())
                                                 .createConnection(input);
            }
        };
    }

    public static Function<? super UserConnection, ? extends ConnectionData> toConnectionData(final TextEncryptor textEncryptor) {
        return new Function<UserConnection, ConnectionData>() {
            @Override
            public ConnectionData apply(final UserConnection input) {

                return input == null
                       ? null
                       : new ConnectionData(input.getProviderId(),
                                            input.getProviderUserId(),
                                            input.getDisplayName(),
                                            input.getProfileUrl(),
                                            input.getImageUrl(),
                                            textEncryptor.encrypt(input.getAccessToken()),
                                            textEncryptor.encrypt(input.getSecret()),
                                            textEncryptor.encrypt(input.getRefreshToken()),
                                            input.getExpireTime());
            }
        };
    }

    public static Function<? super ConnectionData, ? extends UserConnection> toUserConnection(final TextEncryptor textEncryptor,
                                                                                              final Integer rank,
                                                                                              final User user) {
        return toUserConnection(new UserConnection(),
                                textEncryptor,
                                rank,
                                user);
    }

    public static Function<? super ConnectionData, ? extends UserConnection> toUserConnection(final UserConnection userConnection,
                                                                                              final TextEncryptor textEncryptor,
                                                                                              final Integer rank,
                                                                                              final User user) {
        return new Function<ConnectionData, UserConnection>() {
            @Override
            public UserConnection apply(final ConnectionData input) {

                if (input == null) {
                    return null;
                }

                userConnection.setDisplayName(input.getDisplayName());
                userConnection.setExpireTime(input.getExpireTime());
                userConnection.setImageUrl(input.getImageUrl());
                userConnection.setProfileUrl(input.getProfileUrl());
                userConnection.setProviderId(input.getProviderId());
                userConnection.setProviderUserId(input.getProviderUserId());
                userConnection.setRank(rank);
                userConnection.setUser(user);

                if (input.getAccessToken() != null) {
                    userConnection.setAccessToken(textEncryptor.encrypt(input.getAccessToken()));
                }

                if (input.getRefreshToken() != null) {
                    userConnection.setRefreshToken(textEncryptor.encrypt(input.getRefreshToken()));
                }

                if (input.getSecret() != null) {
                    userConnection.setSecret(textEncryptor.encrypt(input.getSecret()));
                }

                return userConnection;
            }
        };
    }

    public static Function<? super ConnectionData, ? extends UserConnection> toUserConnection(final UserConnection userConnection,
                                                                                              final TextEncryptor textEncryptor,
                                                                                              final User user) {

        return toUserConnection(userConnection,
                                textEncryptor,
                                userConnection.getRank(),
                                user);

    }

}
