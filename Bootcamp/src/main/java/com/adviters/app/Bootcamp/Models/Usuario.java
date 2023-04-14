package com.adviters.app.Bootcamp.Models;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID id;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private String lastname;

    @Column(precision = 15)
    @NonNull
    private Integer phone;

    @Column(unique = true)
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    private String street;

    @Column
    private Integer street_number;

    @Column
    private String town;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    @Lob
    private String profile_picture;

    //poner conexion con rol_id
    @OneToMany
    private List<Usuario> idLicencia;

    @Column
    @NonNull
    private Date birth_date;

    @Column
    @NonNull
    private Integer available_days;

    @Column
    @NotNull
    private Boolean supervisor;

    //este campo deberíamos poner el id del usuario que lo creo que
    //que dicho id va a ser enviado por el front
    @Column
    @NonNull
    private UUID createdBy;

    @CreatedDate
    @Column
    private Date createdAt;

    @Column
    @NonNull
    private Boolean isDisabled;
}
