package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.LicenciaServices;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import com.adviters.app.Bootcamp.dtos.UsuarioLicencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/licencias")
public class LicenciaController {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private LicenciaServices services;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    //mapear la lista, por cada licencia de la lista, instanciar el DTO de usuarioLicencia,
    // el nombre lo vas a sacar del usuarioRepository (licencia.usuario.nombre).
    // el id del usuario lo mismo (licencia.usuario.id)
    // la licencia es la licencia total.
    // retornar la lista de los DTOs
    public ResponseEntity<List<UsuarioLicencia>> obtenerUsuariosLicencias() {
        List<Licencia> licencias = licenciaRepository.findAll();

        // Crear una lista de DTOs
        List<UsuarioLicencia> usuariosLicencias = new ArrayList<>();
       /* for (Licencia licencia : licencias) {
            Usuario usuario = UsuarioRepository.findById(licencia.getUsuario().getId());
            UsuarioLicencia usuarioLicencia = new UsuarioLicencia();
            usuarioLicencia.setUsuarioId(usuario.getId());
            usuarioLicencia.setName(usuario.getName());
            usuarioLicencia.setLicencia(licencia);
            usuariosLicencias.add(usuarioLicencia);
            // crear DTO Licencia
            // tiene que tener idLicencia, startDate, endDate, estadoLicencia, tipoLicencia
            //Crear un servicio que lo setee el DTO, en el parametro pasar el usuario o la licencia
        }*/

        if (usuariosLicencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuariosLicencias);
        }
    }
    //Alta de licencias por usuario
    @PostMapping(value = "/usuario/{id}")
    public ResponseEntity agregarLicencia(@RequestBody Licencia licencia, @PathVariable UUID id) throws Exception {
        Usuario user = usuarioRepository.findById(id).get();

        if (user != null) {
            try {
                if (services.checkLicencia(licencia, user)) {
                    Licencia nuevaLicencia = licenciaRepository.save(licencia);
                    return new ResponseEntity<>(nuevaLicencia, HttpStatus.CREATED);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage() + "\n Causa: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
            throw new Exception("Error, no se encuentra el usuario");
    }

    //Obtener todas las licencias por ID
    @GetMapping("/{idLicencias}")
    public ResponseEntity<Licencia> obtenerLicenciaPorId(@PathVariable Long idLicencias) {
        return licenciaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //concatetar que retorne las 3 cosas(tipo de licencia, estado de licencia y licencia)

    }

    //Obtener todas las licencias del usuario
    /*@GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Licencia>> obtenerLicenciasPorUsuarioId(@PathVariable UUID idUsuario) {
        List<Licencia> licencias = licenciaRepository.findByUsuarioId(idUsuario);
        if (licencias.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(licencias);
        }
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Licencia> actualizarLicencia(@PathVariable Long id, @RequestBody Licencia licencia) {
        return licenciaRepository.findById(id)
                .map(l -> {
                    l.setEndDate(licencia.getEndDate());
                    l.setUsuario(licencia.getUsuario());
                    return ResponseEntity.ok(licenciaRepository.save(l));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}