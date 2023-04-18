package com.adviters.app.Bootcamp.Controllers;

import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.Security.JWTCreator;
import com.adviters.app.Bootcamp.Security.JWTObject;
import com.adviters.app.Bootcamp.Security.SecurityConfig;
import com.adviters.app.Bootcamp.dtos.Login;
import com.adviters.app.Bootcamp.dtos.Sesion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    
    private static final Logger logger = LoggerFactory.getLogger("Liberty");

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
                    jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION) ));
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

    @GetMapping("/token")
    public ResponseEntity<?> checkToken() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            logger.info("Datos de los permisos: " + auth.getAuthorities());
            logger.info("Datos del usuario: " + auth.getPrincipal());
            logger.info("Datos de credencial: " + auth.getCredentials());
            logger.info("Detalles: " + auth.getDetails());
            logger.info("Esta autenticado: " + auth.isAuthenticated());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}