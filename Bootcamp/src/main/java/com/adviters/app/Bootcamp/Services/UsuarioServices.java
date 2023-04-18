package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Controllers.UsuarioDTOS.UsuarioRolDTO;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UsuarioServices {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    @PersistenceContext
    private EntityManager entityManager; //esto va a servir para dsp sin necesitamos consultas especificas
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

    public List<UsuarioRolDTO> getUsuariosByRol() {
        List<UsuarioRolDTO> list = (List<UsuarioRolDTO>) repository.findAll().stream().map(user -> {
            UsuarioRolDTO dto = new UsuarioRolDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setSupervisor(user.getRoles().get(0).equals("SUPERVISOR"));
            return dto;
        });
        return list;
    };
}
