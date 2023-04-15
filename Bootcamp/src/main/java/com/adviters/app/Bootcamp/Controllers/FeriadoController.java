package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Feriados.Feriado;
import com.adviters.app.Bootcamp.Repositories.FeriadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeriadoController {

    @Autowired
    private FeriadoRepository feriadoRepository;

    @PostMapping(value = "/feriado")
    public ResponseEntity<Feriado> createFeriado(@RequestBody Feriado feriado) {
        try {
            Feriado savedFeriado = feriadoRepository.save(feriado);
            return new ResponseEntity<>(savedFeriado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}