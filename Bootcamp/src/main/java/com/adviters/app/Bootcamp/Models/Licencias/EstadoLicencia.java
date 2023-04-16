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
    //Van a haber tres tipos de estados: "aprobado", "pendiente" y "rechazado".
    //Estas van a tener una relacion de uno a muchos con las licencias.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID idState;

    @Column
    @NotNull
    private String description;

    //aca va la relacion


}
