package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioRolDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioSupervisedBy;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
           if(usuarioServices.checkIfMayorEdad(usuario.getBirth_date())){
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
                if(!usuario.getRoles().contains("SUPERVISOR") && !usuario.getRoles().contains("USUARIO")){
                    usuario.setRoles(new ArrayList<>(null));
                } else if(!searchUser.getBirth_date().equals(usuario.getBirth_date())){

                }
                usuarioRepository.save(usuario);
                return new ResponseEntity("Usuario actualizado", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity("No se ha podido actualizar el usuario", HttpStatus.BAD_REQUEST);
            }}catch(Exception e){
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
    public List<UsuarioSupervisedBy> getUsuariosSupervisedBy(@PathVariable UUID supervisorId) throws Exception {
        if(!usuarioServices.checkIfSupervisor(supervisorId)) {
            throw new Exception("El usuario ingresado no es supervisor");
        }
        return usuarioServices.getSupervisedUsersById(supervisorId);
    }
}
