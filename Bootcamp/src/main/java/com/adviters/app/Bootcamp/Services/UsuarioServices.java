package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UsuarioServices {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    public void createUser(Usuario user) throws RuntimeException{
        try {
            String pass = user.getPassword();
            //encriptando la contraseña
            user.setPassword(encoder.encode(pass));

            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear el usuario: " + user +"\n" + e + " Causa: "+e.getCause()
 + " \n Mensaje: " + e.getMessage());
        }
    }

    public Boolean checkUser(Usuario usuario) throws RuntimeException{
      if (usuario.getName() == null) {
            throw new RuntimeException("Debe ingresar un nombre");
        } else if (usuario.getLastname() == null) {
            throw new RuntimeException("Debe ingresar un apellido");
        }else if (usuario.getPhone() == null) {
            throw new RuntimeException("Debe ingresar un numero de tel.");
        }else if (usuario.getEmail() == null) {
            throw new RuntimeException("Debe ingresar un correo");
        }else if (usuario.getPassword() == null) {
            throw new RuntimeException("Debe ingresar una contraseña");
        }else if (usuario.getRoles() == null) {
            throw new RuntimeException("Debe ingresar un rol");
        }else if (usuario.getBirth_date() == null) {
            throw new RuntimeException("Debe ingresar su cumpleaños");
        }else if (usuario.getAvailable_days() == null) {
            throw new RuntimeException("Debe ingresar los dias disp.");
        } else {
            return true;
        }
    };
}
