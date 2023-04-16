package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Security.JWTCreator;
import com.adviters.app.Bootcamp.Security.JWTObject;
import com.adviters.app.Bootcamp.Security.SecurityConfig;
import com.adviters.app.Bootcamp.dtos.Login;
import com.adviters.app.Bootcamp.dtos.Sesion;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api")
public class AuthController {
    @Autowired

    private PasswordEncoder encoder;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public String welcome(){
        return "Bienvenido a la api de Liberty";
    }
    @PostMapping("/login")
    public Sesion login(@RequestBody Login login) {
        try{
                Usuario user = repository.findByEmail(login.getEmail());
                if(user!=null) {
                    boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
                    if(!passwordOk) {
                        throw new RuntimeException("Contraseña invalida para logear " + login.getEmail());
                    }
                    //Enviamos un objeto sesion para retornar mas informacion del usuario
                    Sesion sesion = new Sesion();
                    sesion.setLogin(user.getEmail());
                    JWTObject jwtObject = new JWTObject();
                    jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
                    jwtObject.setExpiration((new Date(System.currentTimeMillis() + securityConfig.EXPIRATION) ));
                    jwtObject.setRoles(user.getRoles());
                    sesion.setToken(JWTCreator.createToken(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
                    sesion.setUser(user);
                    return sesion;
            } else {
                throw new RuntimeException("Correo no existente");
            }

        }catch (Exception e) {
            throw new RuntimeException("Mensaje: " + e.getMessage() +"\n"
                    + "Causa: " + e.getCause() + "\n Email: " +
                    login.getEmail() + "\n Password: " + login.getPassword() );
        }
    }
}
