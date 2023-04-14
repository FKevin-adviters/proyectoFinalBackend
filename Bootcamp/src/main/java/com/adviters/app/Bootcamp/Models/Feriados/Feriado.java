package com.adviters.app.Bootcamp.Models.Feriados;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Feriado {

    @Column
    @NotNull
    private UUID idFeriado;

    @Column
    @NotNull
    private String descripcion;

    @Column
    @NotNull
    private Date fecha;

    @Column
    @NotNull
    private Date fechaCreada;

    @Column
    @NotNull
    private String creadoPor;

    @Column
    private Date fechaActualizada;

    @Column
    private String actualizadoPor;

    //no hay ninguna relacion asi que en este no va nada :P


}
