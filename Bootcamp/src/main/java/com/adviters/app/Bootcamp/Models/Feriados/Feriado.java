package com.adviters.app.Bootcamp.Models.Feriados;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Usuario createdBy;

    @Column
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Usuario updatedBy;

    @Column
    private boolean deleted;

    public Feriado() {

    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
