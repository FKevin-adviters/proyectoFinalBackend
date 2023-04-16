package com.adviters.app.Bootcamp.Models.Licencias;

import com.adviters.app.Bootcamp.Models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private Long licenseId;

    @Column
    @NonNull
    private Date startDate;

    @Column
    @NonNull
    private Date endDate;

    @Column
    @NonNull
    private Integer availableDays;

    @Column
    private String documentation;

    @Column(updatable = false)
    @NonNull
    @CreationTimestamp
    private Timestamp createdDate;

    @Column
    @NonNull
    private String createdBy;

    @Column
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column
    private String updatedBy;

    //aca va la relacion
    @ManyToOne
    @JoinColumn(name = "license_type")
    private TipoLicencia tipoLicencia;

    @ManyToOne
    @JoinColumn(name = "license_state")
    private EstadoLicencia estadoLicencia;

    @ManyToOne
    @JoinColumn(name = "license_user")
    private Usuario usuario;

}
