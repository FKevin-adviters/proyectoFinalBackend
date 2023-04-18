package com.adviters.app.Bootcamp.Controllers.UsuarioDTOS;

import java.util.List;
import java.util.UUID;

public class UsuarioRolDTO {

    private UUID id;

    private String name;

    private Boolean isSupervisor;

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

    public Boolean getSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(Boolean supervisor) {
        isSupervisor = supervisor;
    }
}
