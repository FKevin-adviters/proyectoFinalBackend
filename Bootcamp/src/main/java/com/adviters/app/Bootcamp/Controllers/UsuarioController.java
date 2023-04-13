package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController

public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping(value = "/usuario")
    public ResponseEntity<List<Usuario>> getUsuario(){
        List<Usuario> list = usuarioRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping(value = "/usuario/{id}")
    public Usuario getUsuarioById(@PathVariable UUID id){
        Usuario user = usuarioRepository.getById(id);
        return user;
    }
    @PostMapping(value = "/usuario")
    public ResponseEntity saveDiciplina(@RequestBody Usuario usuario){
       try{
           Usuario _user = usuarioRepository.save(usuario);
                   return new ResponseEntity<>(usuario, HttpStatus.CREATED);
       } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
