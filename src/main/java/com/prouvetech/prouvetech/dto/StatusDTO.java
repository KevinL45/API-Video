package com.prouvetech.prouvetech.dto;

public class StatusDTO {

    private long id;
    private String name;

    public StatusDTO() {

    }

    public StatusDTO(long id, String name) {
        this.id = id;
        this.name = name;
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

    public void setId(Long id) {
        this.id = id;
    }

}
