package com.adviters.app.Bootcamp.dtos.Licencias;

import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioDTO;
import lombok.Data;

import java.util.Date;

@Data
public class LicenciaDTO {
    private Long licenseId;
    private Long licenseTypeId;
    private Integer requiredDays;
    private String status;
    private Date startDate;
    private Date endDate;
    private String description;
    private UsuarioDTO usuarioDTO;
}
