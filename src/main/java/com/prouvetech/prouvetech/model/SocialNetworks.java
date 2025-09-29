package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "social_networks", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class SocialNetworks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String logo;

    @OneToMany(mappedBy = "socialNetwork", cascade = CascadeType.ALL)
    private List<UserSocialNetworks> users = new ArrayList<>();

    public SocialNetworks() {
    }

    public SocialNetworks(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logo;
    }

    public void setLogoUrl(String logo) {
        this.logo = logo;
    }

    // === toString ===

    @Override
    public String toString() {
        return "SocialNetwork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
