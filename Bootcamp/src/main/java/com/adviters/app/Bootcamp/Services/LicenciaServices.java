package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
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
}