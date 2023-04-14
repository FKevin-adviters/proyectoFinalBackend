package com.adviters.app.Bootcamp.Models.Licencias;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class TipoLicencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID idLicencia;

    @Column
    @NotNull
    private String descripcion;

    @Column
    @NotNull
    private Boolean docRequerida;

    @Column
    @NotNull
    private Date fechaCreacion;

    @Column
    @NotNull
    private String creadoPor;

    @Column
    private Date fechaActualizacion;

    @Column
    private String actualizadoPor;

    //aca va la relacion
}
