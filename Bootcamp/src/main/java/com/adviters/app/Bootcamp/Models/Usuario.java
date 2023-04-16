package com.adviters.app.Bootcamp.Models;
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
    private Integer phone;

    @Column(unique = true)
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    @NonNull
    private Long dni;

    @Column
    @NonNull
    private Long cuil;

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

    @Column
    private UUID created_by;

    @CreatedDate
    @Column
    private Date createdAt;

    public Usuario() {
    }


}
