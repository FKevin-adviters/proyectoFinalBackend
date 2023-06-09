package com.adviters.app.Bootcamp.Models;
import com.adviters.app.Bootcamp.Models.Feriados.Feriado;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

    @Data
    @AllArgsConstructor
    @Entity
public class Usuario {

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Feriado> feriados;

    @JsonIgnore
    @OneToMany(mappedBy = "updatedBy")
    private List<Feriado> feriadosActualizados;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private String lastname;

    @Column
    @NonNull
    private String phone;

    @Column(unique = true)
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    @NonNull
    private String dni;

    @Column
    @NonNull
    private String cuil;

    @Column
    private String street;

    @Column
    private Integer floor_number;

    @Column
    private String tower;

    @Column
    private Integer street_number;

    @Column
    private Integer postal_code;

    @Column
    private String town;

    @Column
    private String apartment;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    @Lob
    private String profile_picture;

    //poner conexion con rol_id
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private List<String> roles;

    @Column
    @NonNull
    private Date birth_date;

    @Column
    @NonNull
    private Integer available_days;

    @CreatedDate
    @Column
    private Date createdAt;

    @Column
    private UUID supervisorId;

    @Column
    private boolean deleted;

    public Usuario() {
    }

    public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }
}
