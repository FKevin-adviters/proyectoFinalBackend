package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.LicenciaServices;
import com.adviters.app.Bootcamp.Services.ServiciosGenerales;
import com.adviters.app.Bootcamp.dtos.Licencias.LicenciaDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/licencias")
public class LicenciaController {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private LicenciaServices services;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/")
    public ResponseEntity<?> obtenerUsuariosLicencias() {
        List<Licencia> licencias = licenciaRepository.findAll();
        if(licencias.isEmpty()) {
            return new ResponseEntity<>("No hay licencias en la base de datos.", HttpStatus.BAD_REQUEST);
        }
        // Crear una lista de DTOs
        List<HashMap<String, Object>> usuariosLicencias = new ArrayList<>();
        for (Licencia licencia : licencias) {
            HashMap<String, Object> mapaUserLicense = new HashMap<>();
            HashMap<String, Object> newLicencia = new HashMap<>();
            Usuario usuario = licencia.getUsuario();
            //creamos el usuario en su dto
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setName(usuario.getName());
            usuarioDTO.setProfile_picture(usuario.getProfile_picture());
            //creamos la licencia en su nuevo mapa
            newLicencia.put("license_id", licencia.getLicenseId());
            newLicencia.put("state", licencia.getEstadoLicencia().getIdState());
            newLicencia.put("license_type_id", licencia.getTipoLicencia().getLicenseId());
            newLicencia.put("startDate", licencia.getStartDate());
            newLicencia.put("endDate", licencia.getEndDate());
            //los agregamos a mapaUserLicense
            mapaUserLicense.put("user", usuarioDTO);
            mapaUserLicense.put("license", newLicencia);
            //y lo agregamos a la lista
            usuariosLicencias.add(mapaUserLicense);
            // crear DTO Licencia
            // tiene que tener idLicencia, startDate, endDate, estadoLicencia, tipoLicencia
            //Crear un servicio que lo setee el DTO, en el parametro pasar el usuario o la licencia
        }
        return ResponseEntity.ok(usuariosLicencias);
    }

    //Alta de licencias por usuario
    @PostMapping(value = "/usuario/{id}")
    public ResponseEntity agregarLicencia(@RequestBody Licencia licencia, @PathVariable UUID id) {
        try {
                Usuario usuario = usuarioRepository.findById(id).get();
                if(usuario != null){
                    Licencia nuevaLicencia = licenciaRepository.save(licencia);
                    nuevaLicencia.setUsuario(usuario);
                    licenciaRepository.save(nuevaLicencia);
                    return new ResponseEntity<>(nuevaLicencia, HttpStatus.CREATED);
                }
                return new ResponseEntity<>("No se ha podido encontrar el usuario especificado", HttpStatus.NOT_FOUND);

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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLicencia(@PathVariable Long id, @RequestBody Licencia licencia) {
        Licencia licencia1 = licenciaRepository.findById(id).get();
        Usuario usuario = usuarioRepository.findById(licencia1.getUsuario().getId()).get();
        if(licencia1 == null) {
            return new ResponseEntity<>("No se ha encontrado la licencia", HttpStatus.BAD_REQUEST);
        }
        ServiciosGenerales.copyNonNullProperties(licencia, licencia1);
        //pendiente
        //habría q notificar al usuario
        if (/*pendiente*/ licencia1.getEstadoLicencia().getIdState() == 0 || licencia1.getEstadoLicencia().getIdState() == 1 /*rechazado: habría q notificar al user*/) {
        } else if (licencia1.getEstadoLicencia().getIdState() == 2) {
            if (usuario.getAvailable_days() > licencia1.getRequiredDays()) {
                usuario.setAvailable_days(usuario.getAvailable_days() - licencia1.getRequiredDays());
                usuarioRepository.save(usuario);
            } else {
                return new ResponseEntity<>("No tiene los días disponibles para aprobar la licencia", HttpStatus.BAD_REQUEST);
            }
        }
        licenciaRepository.save(licencia1);
        return new ResponseEntity<>("Licencia actualizada", HttpStatus.OK);
    }

    @GetMapping(value = "/usuario/{idUser}/{idLicense}")
    public ResponseEntity getOneLicenseByUserId(@PathVariable UUID idUser, @PathVariable Long idLicense) throws Exception {
        LicenciaDTO licenciaDTO = services.getOneLicenseByUserAndLicenseID(idUser, idLicense);
        return new ResponseEntity(licenciaDTO, HttpStatus.OK);
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