package org.unidle.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import static java.lang.String.format;

@AttributeOverride(column = @Column(name = "id", nullable = false),
                   name = "id")
@Entity
@Table(name = "user_connections",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "provider_id", "provider_user_id"}))
public class UserConnection extends AbstractPersistable<Long> {

    @Column(name = "access_token",
            nullable = false)
    private String accessToken;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "expire_time")
    private Long expireTime;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "provider_id",
            nullable = false)
    private String providerId;

    @Column(name = "provider_user_id")
    private String providerUserId;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "revision")
    @Version
    private Integer revision;

    @Column(name = "secret")
    private String secret;

    @JoinColumn(name = "user_id",
                nullable = false)
    @ManyToOne(optional = false)
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(final Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(final String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(final String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(final Integer rank) {
        this.rank = rank;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(final Integer revision) {
        this.revision = revision;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return format(
                "UserConnection(accessToken='%s', displayName='%s', expireTime=%d, imageUrl='%s', profileUrl='%s', providerId='%s', providerUserId='%s', rank=%d, refreshToken='%s', revision=%d, secret='%s', user=%s)",
                accessToken,
                displayName,
                expireTime,
                imageUrl,
                profileUrl,
                providerId,
                providerUserId,
                rank,
                refreshToken,
                revision,
                secret,
                user);
    }

}
