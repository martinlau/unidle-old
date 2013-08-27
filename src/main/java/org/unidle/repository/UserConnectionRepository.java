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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unidle.domain.User;
import org.unidle.domain.UserConnection;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserConnectionRepository extends JpaRepository<UserConnection, UUID> {

    @Query("DELETE FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId AND uc.providerUserId = :providerUserId")
    @Modifying
    void delete(@Param("user") final User user,
                @Param("providerId") final String providerId,
                @Param("providerUserId") final String providerUserId);

    @Query("DELETE FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId")
    @Modifying
    void delete(@Param("user") final User user,
                @Param("providerId") final String providerId);

    @Query("SELECT uc FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId")
    List<UserConnection> findAll(@Param("user") final User user,
                                 @Param("providerId") final String providerId);

    @Query("SELECT uc FROM UserConnection uc WHERE uc.user = :user ORDER BY uc.providerId, uc.rank")
    List<UserConnection> findAll(@Param("user") final User user);

    @Query("SELECT uc FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId AND uc.providerUserId IN (:providerUserIds) ORDER BY uc.providerId, uc.rank")
    List<UserConnection> findAll(@Param("user") final User user,
                                 @Param("providerId") final String providerId,
                                 @Param("providerUserIds") final List<String> providerUserIds);

    @Query("SELECT uc FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId AND uc.providerUserId = :providerUserId")
    UserConnection findOne(@Param("user") final User user,
                           @Param("providerId") final String providerId,
                           @Param("providerUserId") final String providerUserId);

    @Query("SELECT uc FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId AND uc.rank = 1")
    UserConnection findPrimaryConnection(@Param("user") final User user,
                                         @Param("providerId") final String providerId);

    @Query("SELECT COALESCE(max(uc.rank) + 1, 1) FROM UserConnection uc WHERE uc.user = :user AND uc.providerId = :providerId")
    Integer findRank(@Param("user") final User user,
                     @Param("providerId") final String providerId);

    @Query("SELECT DISTINCT uc.user.id FROM UserConnection uc WHERE uc.providerId = :providerId AND uc.providerUserId IN :providerUserIds")
    List<Long> findUserIdsConnectedTo(@Param("providerId") final String providerId,
                                      @Param("providerUserIds") final Set<String> providerUserIds);

    @Query("SELECT DISTINCT uc.user.id FROM UserConnection uc WHERE uc.providerId = :providerId AND uc.providerUserId = :providerUserId")
    List<Long> findUserIdsWithConnection(@Param("providerId") final String providerId,
                                         @Param("providerUserId") final String providerUserId);

}
