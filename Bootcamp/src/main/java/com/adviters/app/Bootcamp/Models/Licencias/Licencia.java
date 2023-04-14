package com.adviters.app.Bootcamp.Models.Licencias;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID idLicencia;

    //aca irian tres Columns que vienen de las relaciones

    @Column
    @NotNull
    private Date fechaInicio;

    @Column
    @NotNull
    private Date fechaFin;

    @Column
    @NotNull
    private Integer diasDisponibles;

    @Column
    private String documentacion;

    @Column
    @NotNull
    private Date fechaCreada;

    @Column
    @NotNull
    private String creadaPor;

    @Column
    private Date fechaActualizada;

    @Column
    private String actualizadaPor;

    //aca va la relacion



}
