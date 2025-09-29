package com.prouvetech.prouvetech.model;

import com.prouvetech.prouvetech.compositekey.UserSocialNetworksId;

import jakarta.persistence.*;

@Entity
@Table(name = "user_social_networks", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "social_networks_id" })
})
public class UserSocialNetworks {

    @EmbeddedId
    private UserSocialNetworksId userSocialNetworksId;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne(optional = false)
    @MapsId("socialNetworkId")
    @JoinColumn(name = "social_networks_id", nullable = true)
    private SocialNetworks socialNetwork;

    @Column(nullable = false, length = 255)
    private String profileUrl;

    public UserSocialNetworks() {
    }

    public UserSocialNetworks(User user, SocialNetworks socialNetwork, String profileUrl) {
        this.user = user;
        this.socialNetwork = socialNetwork;
        this.profileUrl = profileUrl;
    }

    public UserSocialNetworksId getUserSocialNetworksId() {
        return this.userSocialNetworksId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialNetworks getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetworks socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setUserSocialNetworksId(UserSocialNetworksId userSocialNetworksId) {
        this.userSocialNetworksId = userSocialNetworksId;
    }

    @Override
    public String toString() {
        return "UserSocialNetwork{" +
                ", user=" + user.getId() +
                ", socialNetwork=" + socialNetwork.getName() +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }
}
