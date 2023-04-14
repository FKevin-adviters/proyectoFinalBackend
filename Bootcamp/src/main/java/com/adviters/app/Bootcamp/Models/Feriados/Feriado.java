package com.adviters.app.Bootcamp.Models.Feriados;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    private UUID holidayId;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Date date;

    @Column
    @NotNull
    private Date createdDate;

    @Column
    @NotNull
    private String createdBy;

    @Column
    private Date updatedDate;

    @Column
    private String updatedBy;

    //no hay ninguna relacion asi que en este no va nada :P

}
