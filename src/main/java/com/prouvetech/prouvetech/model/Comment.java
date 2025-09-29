package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.prouvetech.prouvetech.compositekey.CommentId;

@Entity
@Table(name = "comments")
public class Comment {

    @EmbeddedId
    private CommentId commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = true)
    private LocalDateTime deleteDate;

    public Comment() {
        this.createDate = LocalDateTime.now();
    }

    public Comment(User user, Project project, String content) {
        this.user = user;
        this.project = project;
        this.content = content;
        this.createDate = LocalDateTime.now();
    }

    public CommentId getCommentId() {
        return this.commentId;
    }

    public void setCommentId(CommentId commentId) {
        this.commentId = commentId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
