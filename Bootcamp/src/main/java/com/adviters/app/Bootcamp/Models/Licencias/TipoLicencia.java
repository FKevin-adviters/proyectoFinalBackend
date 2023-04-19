package com.adviters.app.Bootcamp.Models.Licencias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

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

    public TipoLicencia() {
    }
}
