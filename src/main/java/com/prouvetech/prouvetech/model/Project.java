package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String video;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = true)
    private String sourceCode;

    @Column(nullable = true)
    private String videoExplained;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = true)
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation Many-to-Many avec Tool
    @ManyToMany
    @JoinTable(name = "project_tools", // Table de jointure
            joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private List<Tool> tools = new ArrayList<>();

    // Relation Many-to-Many avec Document
    @ManyToMany
    @JoinTable(name = "project_documents", // Table de jointure
            joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "document_id"))
    private List<Document> documents = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String description, String video, String thumbnail, String sourceCode, User user,
            List<Document> documents, List<Tool> tools,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        this.name = name;
        this.description = description;
        this.video = video;
        this.thumbnail = thumbnail;
        this.sourceCode = sourceCode;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.documents = documents;
        this.tools = tools;
    }

    public Project(String name, String description, String video, String thumbnail, String sourceCode, User user,
            List<Tool> tools,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        this.name = name;
        this.description = description;
        this.video = video;
        this.thumbnail = thumbnail;
        this.sourceCode = sourceCode;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.tools = tools;
    }

    public Project(String name, String description, String video, String thumbnail, String sourceCode, User user,
            LocalDateTime updateDate) {
        this.name = name;
        this.description = description;
        this.video = video;
        this.thumbnail = thumbnail;
        this.sourceCode = sourceCode;
        this.user = user;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getVideoExplained() {
        return videoExplained;
    }

    public void setVideoExplained(String videoExplained) {
        this.videoExplained = videoExplained;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Document> getDocuemnts() {
        return this.documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", video='" + video + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", videoExplained='" + videoExplained + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleteDate=" + deleteDate +
                ", tools=" + tools +
                ", documents=" + documents +
                '}';
    }
}
