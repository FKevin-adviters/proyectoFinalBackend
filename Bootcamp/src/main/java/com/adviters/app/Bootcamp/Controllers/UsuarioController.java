package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Controllers.UsuarioDTOS.UsuarioRolDTO;
import com.adviters.app.Bootcamp.Controllers.UsuarioDTOS.UsuarioSupervisedBy;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping(value = "/usuario")
    public ResponseEntity<List<Usuario>> getUsuario(){
        List<Usuario> list = usuarioRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping(value = "/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable UUID id){
        Usuario user = usuarioRepository.findById(id).get();
        return user;
    }
    @PostMapping(value = "/usuario")
    public ResponseEntity setUsuario(@RequestBody Usuario usuario){
       try{
           usuarioServices.createUser(usuario);
               return new ResponseEntity<>(usuario, HttpStatus.CREATED);
       } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + "\n Causa: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public List<UsuarioSupervisedBy> getUsuariosSupervisedBy(@PathVariable UUID supervisorId) throws Exception {
        if(!usuarioServices.checkIfSupervisor(supervisorId)) {
            throw new Exception("El usuario ingresado no es supervisor");
        }
        return usuarioServices.getSupervisedUsersById(supervisorId);
    }
}
