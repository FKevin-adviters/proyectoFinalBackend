package com.adviters.app.Bootcamp.Models;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private UUID id;

    @Column

    private String name;

    @Column

    private String email;

    @Column

    private String password;

}
