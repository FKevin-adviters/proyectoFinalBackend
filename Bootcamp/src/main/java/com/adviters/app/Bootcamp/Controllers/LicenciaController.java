package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LicenciaController {

    // Inyectamos la dependencia de LicenciaRepository
    @Autowired
    LicenciaRepository licenciaRepository;

    // Definimos un endpoint GET para obtener todas las licencias en el sistema
    @GetMapping(value = "/licencias")
    public ResponseEntity<?> getLicencias() {
        try {
            // Buscamos todas las licencias en la base de datos usando el método findAll() de LicenciaRepository
            List<Licencia> licencia = licenciaRepository.findAll();
            // Retornamos una respuesta HTTP 200 OK con la lista de licencias encontradas
            return new ResponseEntity<>(licencia, HttpStatus.OK);
        } catch (Exception e) {
            // En caso de error, retornamos una respuesta HTTP 204 NO CONTENT con un mensaje de error
            return new ResponseEntity<>("No se han encontrado licencias.", HttpStatus.NO_CONTENT);
        }
    }

    // Definimos un endpoint POST para agregar una nueva licencia a la base de datos
    @PostMapping(value = "/licencia")
    public ResponseEntity<?> addLicencia(@RequestBody Licencia licencia) {
        try {
            // Guardamos la nueva licencia en la base de datos usando el método save() de LicenciaRepository
            Licencia addedLicencia = licenciaRepository.save(licencia);
            // Retornamos una respuesta HTTP 201 CREATED con la licencia agregada
            return new ResponseEntity<>(addedLicencia, HttpStatus.CREATED);
        } catch (Exception e) {
            // En caso de error, retornamos una respuesta HTTP 500 INTERNAL SERVER ERROR con un mensaje de error
            return new ResponseEntity<>("No se ha podido crear la licencia.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Definimos un endpoint GET para obtener una licencia en particular según su id
    @GetMapping(value = "/licencia/{licenciaId}/usuario/{usuarioId}")
    public ResponseEntity<?> getLicenciaById(@PathVariable Long licenciaId) {
        try {
            // Buscamos la licencia en la base de datos usando el método findById() de LicenciaRepository
            Licencia licencia = licenciaRepository.findById(licenciaId).orElse(null);
            // Si encontramos la licencia, retornamos una respuesta HTTP 200 OK con la licencia encontrada
            if (licencia != null) {
                return new ResponseEntity<>(licencia, HttpStatus.OK);
            } else {
                // Si no encontramos la licencia, retornamos una respuesta HTTP 404 NOT FOUND con un mensaje de error
                return new ResponseEntity<>("Licencia no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // En caso de error, retornamos una respuesta HTTP 500 INTERNAL SERVER ERROR con un mensaje de error
            return new ResponseEntity<>("Error al buscar la licencia.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Definimos un   método para obtener las licencias de un usuario en particular a través de su ID
    @GetMapping(value = "/licencia/usuario/{usuarioId}")
    public ResponseEntity<?> getLicenciasByUsuarioId(@PathVariable Long usuarioId) {
        try {
            // Buscamos las licencias utilizando el método findByUsuarioId del repositorio de licencias
            List<Licencia> licencias = licenciaRepository.findByUsuarioId(usuarioId);
            // Si encontramos licencias, retornamos una respuesta HTTP 200 OK con las licencias encontradas
            if (!licencias.isEmpty()) {
                return new ResponseEntity<>(licencias, HttpStatus.OK);
            } else {
                // Si no encontramos licencias, retornamos una respuesta HTTP 404 NOT FOUND con un mensaje de error
                return new ResponseEntity<>("No se han encontrado licencias para el usuario indicado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // En caso de error, retornamos una respuesta HTTP 500 INTERNAL SERVER ERROR con un mensaje de error
            return new ResponseEntity<>("Error al buscar las licencias del usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Definimos un método para actualizar una licencia existente
    @PutMapping(value = "/licencia/{licenciaId}")
    public ResponseEntity<?> updateLicencia(@PathVariable Long licenciaId, @RequestBody Licencia licencia) {
        try {
            // Buscamos la licencia a actualizar utilizando el método findById del repositorio de licencias
            Licencia updatedLicencia = licenciaRepository.findById(licenciaId).map(l -> {
                // Actualizamos la fecha de finalización y el usuario de la licencia
                l.setEndDate(licencia.getEndDate());
                l.setUsuario(licencia.getUsuario());
                // Guardamos los cambios en el repositorio de licencias y retornamos la licencia actualizada
                return licenciaRepository.save(l);
            }).orElse(null);

            // Si encontramos la licencia y la actualizamos, retornamos una respuesta HTTP 200 OK con la licencia actualizada
            if (updatedLicencia != null) {
                return new ResponseEntity<>(updatedLicencia, HttpStatus.OK);
            } else {
                // Si no encontramos la licencia, retornamos una respuesta HTTP 404 NOT FOUND con un mensaje de error
                return new ResponseEntity<>("Licencia no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // En caso de error, retornamos una respuesta HTTP 500 INTERNAL SERVER ERROR con un mensaje de error
            return new ResponseEntity<>("Error al actualizar la licencia.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}