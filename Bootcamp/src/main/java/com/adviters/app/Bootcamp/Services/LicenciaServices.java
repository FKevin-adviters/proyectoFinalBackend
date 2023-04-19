package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Models.Usuario;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LicenciaServices {
    @Autowired
    private LicenciaRepository repository;

    public void createLicencia(Licencia licencia) throws RuntimeException{
        try {
            repository.save(licencia);
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear la licencia: " + licencia + "\n" + e + " Causa: " + e.getCause() + " \n Mensaje: " + e.getMessage());
        }
    }

    public Boolean checkLicencia(Licencia licencia, Usuario user) throws Exception {
        if (licencia.getEndDate().before(licencia.getStartDate())){
            throw new Exception("La fecha de fin de la licencia no puede ser anterior a la fecha de inicio.");
        }
        if(licencia.getTipoLicencia().getLicenseId() > 3) {
            throw new Exception("No existe el tipo de licencia");
        }
        if(user.getAvailable_days() < licencia.getRequiredDays()) {
            throw new Exception("No se puede crear la licencia");
        }
        return true;
    };
}