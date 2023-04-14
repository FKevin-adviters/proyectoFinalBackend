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
    private UUID licenseId;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Boolean documentRequired;

    @Column
    @NotNull
    private Date creationDate;

    @Column
    @NotNull
    private String createdBy;

    @Column
    private Date updateDate;

    @Column
    private String updatedBy;

    //aca va la relacion
}
