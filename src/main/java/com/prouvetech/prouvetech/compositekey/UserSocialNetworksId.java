package com.prouvetech.prouvetech.compositekey;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSocialNetworksId implements Serializable {

    private Long userId;
    private Long socialNetworkId;

    public UserSocialNetworksId() {
    }

    public UserSocialNetworksId(Long userId, Long socialNetworkId) {
        this.userId = userId;
        this.socialNetworkId = socialNetworkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserSocialNetworksId))
            return false;
        UserSocialNetworksId that = (UserSocialNetworksId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(socialNetworkId, that.socialNetworkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, socialNetworkId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSocialNetkworks() {
        return this.socialNetworkId;
    }

    public void setSocialNetworksId(Long socialNetworksId) {
        this.socialNetworkId = socialNetworksId;
    }

}
