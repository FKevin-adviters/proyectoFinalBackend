package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LicenciaController {
    @Autowired
    LicenciaRepository licenciaRepository;

    @GetMapping(value = "/licencias")
    public ResponseEntity<?> getLicencias()  {
        try{
            List<Licencia> licencia = licenciaRepository.findAll();
            return new ResponseEntity<>(licencia, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se han encontrado licencias.", HttpStatus.NO_CONTENT);
        }
    }
}
