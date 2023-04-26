package com.doctory.domain;

public class SearchDto {
    private Long id;
    private String name;

    public SearchDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SearchDto() {
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
}
