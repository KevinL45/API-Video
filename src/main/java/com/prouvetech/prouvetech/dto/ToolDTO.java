package com.prouvetech.prouvetech.dto;

import org.springframework.web.multipart.MultipartFile;

public class ToolDTO {

    private long id;
    private String name;
    private String logo;

    private MultipartFile fileLogo;

    public ToolDTO() {

    }

    public ToolDTO(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public ToolDTO(long id, String name, String logo) {
        this.name = name;
        this.logo = logo;
        this.id = id;
    }

    public ToolDTO(MultipartFile fileLogo, String name) {
        this.name = name;
        this.fileLogo = fileLogo;

    }

    public ToolDTO(Long id, String name, String logo) {
        this.name = name;
        this.logo = logo;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setLong(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public MultipartFile getFileLogo() {
        return fileLogo;
    }

    public void setFileLogo(MultipartFile fileLogo) {
        this.fileLogo = fileLogo;
    }

}
