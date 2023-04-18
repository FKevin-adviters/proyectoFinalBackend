package com.adviters.app.Bootcamp.dtos;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;

import java.util.UUID;

public class UsuarioLicencia {

    private UUID usuarioId;
    private String name;
    private Licencia licencia;

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}
