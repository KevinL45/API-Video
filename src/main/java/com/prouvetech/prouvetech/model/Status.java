package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "status", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
