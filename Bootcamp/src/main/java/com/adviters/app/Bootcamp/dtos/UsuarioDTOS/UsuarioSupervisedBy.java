package com.adviters.app.Bootcamp.dtos.UsuarioDTOS;

import java.util.UUID;

public class UsuarioSupervisedBy {
    private String name;
    private String lastname;
    private UUID id;
    private String profile_picture;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getProfile_picture() {
        return profile_picture;
    }
    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
