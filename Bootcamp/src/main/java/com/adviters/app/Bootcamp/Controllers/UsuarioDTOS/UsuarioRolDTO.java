package com.adviters.app.Bootcamp.Controllers.UsuarioDTOS;

import java.util.List;
import java.util.UUID;

public class UsuarioRolDTO {

    private UUID id;

    private String name;

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
