package com.adviters.app.Bootcamp.Models.Licencias;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @NonNull
    private UUID licenseId;

    @Column
    @NotNull
    private Date startDate;

    @Column
    @NotNull
    private Date endDate;

    @Column
    @NotNull
    private Integer availableDays;

    @Column
    private String documentation;

    @Column(updatable = false)
    @NotNull
    @CreationTimestamp
    private Timestamp createdDate;

    @Column
    @NotNull
    private String createdBy;

    @Column
    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column
    private String updatedBy;

    //aca va la relacion



}
