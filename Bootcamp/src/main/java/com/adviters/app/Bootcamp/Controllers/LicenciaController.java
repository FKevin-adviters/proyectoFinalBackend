package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LicenciaController {
    @Autowired
    LicenciaRepository licenciaRepository;

    @GetMapping(value = "/licencia")
    public String getLicencia(){
        return "licenciaCreada";
    }

}
