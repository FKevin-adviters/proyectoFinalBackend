package com.adviters.app.Bootcamp.Models.Licencias;

import com.adviters.app.Bootcamp.Models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class TipoLicencia {
    //Los tipos de licencias que van a haber son 4:
    //"Dia de Estudio", "Vacaciones", "Tramites" y "Licencia Medica"
    //van a tener una relación de "uno a muchos" con las licencias

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private Long licenseId;

    @Column
    @NonNull
    private String name;
}
