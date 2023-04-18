package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServices services;

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
               services.createUser(usuario);
               return new ResponseEntity<>(usuario, HttpStatus.CREATED);
       } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + "\n Causa: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
