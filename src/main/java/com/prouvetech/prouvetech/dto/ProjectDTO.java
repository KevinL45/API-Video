package com.prouvetech.prouvetech.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProjectDTO {
    private long id;
    private String name;
    private String description;
    private String sourcecode;
    private MultipartFile video;
    private String link_video;
    private MultipartFile thumbnail;
    private String link_thumbnail;
    private UserDTO user;
    private List<ToolDTO> tools;

    private List<Long> toolsIds;

    public ProjectDTO() {

    }

    public ProjectDTO(long id, String name, String description, String link_video, String link_thumbnail,
            String soucecode, UserDTO user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sourcecode = soucecode;
        this.link_video = link_video;
        this.link_thumbnail = link_thumbnail;
        this.user = user;
    }

    public ProjectDTO(long id, String name, String description, String link_video, String link_thumbnail,
            String soucecode,
            UserDTO user, List<ToolDTO> tools) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sourcecode = soucecode;
        this.link_video = link_video;
        this.link_thumbnail = link_thumbnail;
        this.tools = tools;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getSourcecode() {
        return sourcecode;
    }

    public void setSourcecode(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    public List<ToolDTO> getTools() {
        return tools;
    }

    public void setTools(List<ToolDTO> tools) {
        this.tools = tools;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink_thumbnail() {
        return link_thumbnail;
    }

    public void setLink_Thumbnail(String link_thumbnail) {
        this.link_thumbnail = link_thumbnail;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getLink_video() {
        return link_video;
    }

    public void setLink_video(String link_video) {
        this.link_video = link_video;
    }

    public List<Long> getToolsIds() {
        return toolsIds;
    }

    public void setToolsIds(List<Long> toolsIds) {
        this.toolsIds = toolsIds;
    }

}
