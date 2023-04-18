package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.LicenciaServices;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import com.adviters.app.Bootcamp.dtos.Licencias.LicenciaDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioLicencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity agregarLicencia(@RequestBody Licencia licencia, @PathVariable UUID id) {
        try {
            /*if (licencia.getEndDate().before(licencia.getStartDate())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("La fecha de fin de la licencia no puede ser anterior a la fecha de inicio.");
            }
            if(licencia.getTipoLicencia().getLicenseId() <= 3) {
                System.out.println(licencia.getTipoLicencia().getLicenseId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(licencia.getTipoLicencia().getLicenseId() + " el tipo de licencia existe");
            }
            if (licencia.getDocumentation().isEmpty()){
                
            }*/
                Licencia nuevaLicencia = licenciaRepository.save(licencia);
                return new ResponseEntity<>(nuevaLicencia, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + "\n Causa: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener detalles de 1 licencia
    @GetMapping("/{idLicencia}")
    public ResponseEntity<Licencia> obtenerLicenciaPorId(@PathVariable Long idLicencia) {
        return licenciaRepository.findById(idLicencia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //concatetar que retorne las 3 cosas(tipo de licencia, estado de licencia y licencia)

    }

    //Obtener todas las licencias del usuario
    @GetMapping("/usuario/{idUsuario}")
    public List<LicenciaDTO> obtenerLicenciasPorUsuarioId(@PathVariable UUID idUsuario) throws Exception {
        List<LicenciaDTO> lista = services.getLicenciasByUserId(idUsuario);
        if(lista.isEmpty()){
            throw new Exception("No se han encontrado las licencias para el usuario.");
        }
        return lista;
    }
    @PutMapping("/{id}")
    public ResponseEntity<Licencia> actualizarLicencia(@PathVariable Long id, @RequestBody Licencia licencia) {
        //comentario: si buscás por id no te sale solo 1 licencia?
        //habría q verificar tmb si la licencia existe y si es igual a la q encontramos con la q
        // nos dio el usuario, dsp de eso guardamos el q nos dio
        // el save puede hacer update y create a la vez
        return licenciaRepository.findById(id)
                .map(l -> {
                    l.setEndDate(licencia.getEndDate());
                    l.setUsuario(licencia.getUsuario());
                    return ResponseEntity.ok(licenciaRepository.save(l));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    //Obtener lista de LicenciasDTO para 1 usuario
    @GetMapping(value = "/usuario/{idUser}/list")
    public ResponseEntity getListLicenciasByUserId(@PathVariable UUID idUser) throws Exception {
        Usuario searchedUser = usuarioRepository.findById(idUser).get();
        if(searchedUser == null) {
            throw new Exception("No se ha encontrado el usuario.");
        }
        List<LicenciaDTO> listLicencia = services.getLicenciasByUserId(idUser);
        if(listLicencia.isEmpty()) {
            return new ResponseEntity("Las licencias para el usuario no fueron encontradas", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(listLicencia, HttpStatus.OK);
    }
}