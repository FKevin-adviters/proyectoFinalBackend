package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Services.ServiciosGenerales;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioRolDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioDTO;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioServices usuarioServices;
    @GetMapping(value = "/usuario")
    public ResponseEntity<?> getUsuario(){
        List<Usuario> list = usuarioRepository.findAll();
        if(list.isEmpty()) {
            return new ResponseEntity<>("No se han encontrado usuarios", HttpStatus.NOT_FOUND);
        }
        List<UsuarioDTO> listDto =  list.stream().map(user -> {

            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(user.getId());
            dto.setProfile_picture(user.getProfile_picture());
            dto.setName(user.getName());
            dto.setLastname(user.getLastname());
            if(user.getSupervisorId() == null) {
                dto.setSupervisor(null);
                return dto;
            }
            Usuario supervisor = usuarioRepository.findById(user.getSupervisorId()).get();
            UsuarioDTO supervisorDTO = new UsuarioDTO();
            supervisorDTO.setSupervisor(null);
            supervisorDTO.setName(supervisor.getName());
            supervisorDTO.setProfile_picture(supervisor.getProfile_picture());
            supervisorDTO.setLastname(supervisor.getLastname());
            supervisorDTO.setId(supervisor.getId());
            dto.setSupervisor(supervisorDTO);
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }
    @GetMapping(value = "/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable UUID id){
        Usuario user = usuarioRepository.findById(id).get();
        return user;
    }
    @PostMapping(value = "/usuario")
    public ResponseEntity setUsuario(@RequestBody Usuario usuario){
       try{
           if(usuarioServices.checkIfMayorEdad(usuario.getBirth_date())){
               usuario.setDeleted(false);
               usuarioServices.createUser(usuario);
               return new ResponseEntity<>(usuario, HttpStatus.CREATED);
           } else {
               return new ResponseEntity<>("El usuario debe ser mayor de edad.", HttpStatus.NOT_ACCEPTABLE);
           }
       } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + "\n Causa: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @PutMapping(value = "/usuario")
    public ResponseEntity updateUsuario(@RequestBody Usuario usuario) throws Exception{
        try{Usuario searchUser = usuarioRepository.findById(usuario.getId()).get();
            if(searchUser != null) {
                ServiciosGenerales.copyNonNullProperties(usuario, searchUser);
                usuarioRepository.save(searchUser);
                return new ResponseEntity("Usuario actualizado", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity("No se ha podido actualizar el usuario", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            throw new Exception("No se ha aceptado la actualizacion de usuario." + e.getMessage());
        }
    }
    @GetMapping(value = "/usuario/roles")
    public HashMap<String, List<UsuarioRolDTO>> getUsuariosByRol() throws Exception {
       try{
           return usuarioServices.getUsuariosByRol();
    } catch (Exception e){
           throw new Exception("Hubo un error al crear la lista: " + e.getMessage() + e);
       }
    }
    @GetMapping(value = "/usuario/supervisor/{supervisorId}")
    public List<UsuarioDTO> getUsuariosSupervisedBy(@PathVariable UUID supervisorId) throws Exception {
        if(!usuarioServices.checkIfSupervisor(supervisorId)) {
            throw new Exception("El usuario ingresado no es supervisor");
        }
        return usuarioServices.getSupervisedUsersById(supervisorId);
    }

    @DeleteMapping("/usuario/delete/{id}")
    public ResponseEntity<HttpStatus>deleteUsuario(@PathVariable UUID id) {
        try {
            usuarioServices.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
