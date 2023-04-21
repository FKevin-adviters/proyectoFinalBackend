package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Feriados.Feriado;
import com.adviters.app.Bootcamp.Repositories.FeriadoRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.FeriadoServices;
import com.adviters.app.Bootcamp.dtos.FeriadoDTO.FeriadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FeriadoController {

    @Autowired
    FeriadoServices feriadoServices;

    @PostMapping("/feriados")
    public ResponseEntity<Feriado> createFeriado(@RequestBody Feriado feriado) {
        Feriado newFeriado = feriadoServices.createFeriado(feriado);
        return new ResponseEntity<>(newFeriado, HttpStatus.CREATED);
    }

    @GetMapping("/feriados/{id}")
    public ResponseEntity<Feriado> getFeriadoById(@PathVariable UUID id) {
        Optional<Feriado> feriadoData = Optional.ofNullable(feriadoServices.getFeriadoById(id));
        return feriadoData.map(feriado -> new ResponseEntity<>(feriado, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/feriados")
    public ResponseEntity<List<FeriadoDTO>> getAllFeriados() {
        List<Feriado> feriados = feriadoServices.getAllFeriados();
        List<FeriadoDTO> feriadosSimples = new ArrayList<>();
        for (Feriado feriado : feriados) {
            FeriadoDTO feriadoSimple = new FeriadoDTO(feriado.getDate(), feriado.getDescription());
            feriadosSimples.add(feriadoSimple);
        }
        return new ResponseEntity<>(feriadosSimples, HttpStatus.OK);
    }

    @PutMapping("/feriados/{id}")
    public ResponseEntity<Feriado> updateFeriado(@PathVariable UUID id, @RequestBody Feriado feriado) {
        Feriado updatedFeriado = feriadoServices.updateFeriado(id, feriado);
        if (updatedFeriado != null) {
            return new ResponseEntity<>(updatedFeriado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/feriados/{id}")
    public ResponseEntity<HttpStatus>deleteFeriado(@PathVariable UUID id) {
        try {
            feriadoServices.deleteFeriado(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
