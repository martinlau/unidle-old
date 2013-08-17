package org.unidle.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import static java.lang.String.format;

@Entity
@Table(name = "USER_CONNECTION",
       uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID",
                                                            "PROVIDER_ID",
                                                            "PROVIDER_USER_ID"}))
public class UserConnection extends AbstractPersistable<Long> {

    @Column(name = "ACCESS_TOKEN",
            nullable = false)
    private String accessToken;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "EXPIRE_TIME")
    private Long expireTime;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "PROVIDER_ID",
            nullable = false)
    private String providerId;

    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;

    @Column(name = "RANK")
    private Integer rank;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "REVISION")
    @Version
    private Integer revision;

    @Column(name = "SECRET")
    private String secret;

    @JoinColumn(name = "USER_ID",
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
