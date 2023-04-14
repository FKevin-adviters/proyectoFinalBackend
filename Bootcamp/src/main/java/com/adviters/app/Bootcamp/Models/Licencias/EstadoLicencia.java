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
public class EstadoLicencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID idEstado;

    @Column
    @NotNull
    private String descripcion;

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

    @Column
    private Boolean estado;

    //aca va la relacion


}
