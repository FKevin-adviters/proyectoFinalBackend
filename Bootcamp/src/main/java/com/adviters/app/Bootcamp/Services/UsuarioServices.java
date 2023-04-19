package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioRolDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioDTO;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional
public class UsuarioServices {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger("Liberty");
    @PersistenceContext
    private EntityManager entityManager; //esto va a servir para dsp si necesitamos consultas especificas

    public UsuarioServices() {
    }

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
    public HashMap<String, List<UsuarioRolDTO>> getUsuariosByRol()  {
        Query query = entityManager.createQuery("SELECT e FROM Usuario e JOIN FETCH e.roles", Usuario.class);

        List<Usuario> usuarioList = query.getResultList();
        List<UsuarioRolDTO> dtoListSupervisores = new ArrayList<>();
        List<UsuarioRolDTO> dtoListUsuarios = new ArrayList<>();
        HashMap<String, List<UsuarioRolDTO>> usuarios = new HashMap<String, List<UsuarioRolDTO>>();

        for (Usuario user : usuarioList) {
            UsuarioRolDTO dto = new UsuarioRolDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setRoles(user.getRoles());
            if(user.getRoles().contains("SUPERVISOR")){
                dtoListSupervisores.add(dto);
            } else {
                dtoListUsuarios.add(dto);
            }
        }
        usuarios.put("supervisores", dtoListSupervisores);
        usuarios.put("usuarios", dtoListUsuarios);
        return usuarios;
    }
    public List<UsuarioDTO> getSupervisedUsersById (UUID id) {
        Query query = entityManager.createQuery("SELECT e FROM Usuario e JOIN FETCH e.roles WHERE e.supervisorId = :supervisorId", Usuario.class);
        query.setParameter("supervisorId", id);
        List<Usuario> userList = query.getResultList();
        List<UsuarioDTO> userDTOList = new ArrayList<>();
        for (Usuario user: userList) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setLastname(user.getLastname());
            dto.setProfile_picture(user.getProfile_picture());
            userDTOList.add(dto);
        }
        return userDTOList;
    }

    public Boolean checkIfSupervisor (UUID id) throws Exception {
        try{
            Query query = entityManager.createQuery("SELECT e FROM Usuario e JOIN FETCH e.roles WHERE e.id = :userId", Usuario.class);
            query.setParameter("userId", id);
            Usuario usuario = (Usuario) query.getSingleResult();
            if(usuario != null) {
                return usuario.getRoles().contains("SUPERVISOR");
            } else {
                throw new Exception("No se ha encontrado el usuario solicitado");
            }
        } catch(Exception e) {
            throw new Exception("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public Boolean checkIfMayorEdad(Date fecha) {
        LocalDate fechaToLocalDate =  LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(fecha));
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(fechaToLocalDate, ahora);
        if(periodo.getYears() < 18){
            System.out.println("No es mayor: ");
            return false;
        }
        System.out.println("No es mayor: ");
        return true;
    }
}
