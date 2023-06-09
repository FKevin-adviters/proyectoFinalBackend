package com.adviters.app.Bootcamp.Models.Licencias;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
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
    private Integer requiredDays;

    @Column
    private String documentation;

    @Column
    private String description;

    @Column(updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column
    @NonNull
    private UUID createdBy;

    @Column
    private Date updatedDate;

    @Column
    private UUID updatedBy;

    //aca va la relacion

    @ManyToOne
    @JoinColumn(name = "license_type", referencedColumnName = "licenseId")
    private TipoLicencia tipoLicencia;

    @ManyToOne
    @JoinColumn(name = "license_state", referencedColumnName = "idState")
    private EstadoLicencia estadoLicencia;

    @ManyToOne
    @JoinColumn(name = "license_user", referencedColumnName = "id")
    @JsonIgnore
    private Usuario usuario;

    @Column
    private UUID licenseSupervisor;

    @Column
    private boolean deleted;

    public Licencia() {
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
