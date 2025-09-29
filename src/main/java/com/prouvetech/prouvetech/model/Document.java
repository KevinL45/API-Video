package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String file;

    // Relation Many-to-Many avec Project
    @ManyToMany(mappedBy = "documents")
    private List<Project> projects = new ArrayList<>();

    public Document() {
    }

    public Document(String name, String file) {
        this.name = name;
        this.file = file;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    // === toString ===

    @Override
    public String toString() {
        return "Docuemnt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
