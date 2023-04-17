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
            if (licencia.getCreatedBy() == null) {
                throw new RuntimeException("Debe ingresar el creador");
            } else if (licencia.getDocumentation() == null) {
                throw new RuntimeException("Debe ingresar la documentación");
            } else if (licencia.getEndDate() == null) {
                throw new RuntimeException("Debe ingresar la fecha de finalización");
            } else if (licencia.getStartDate() == null) {
                throw new RuntimeException("Debe ingresar la fecha de inicio");
            } else if (licencia.getUpdatedBy() == null) {
                throw new RuntimeException("Debe ingresar el actualizador");
            } else {
                repository.save(licencia);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido crear la licencia: " + licencia + "\n" + e + " Causa: " + e.getCause() + " \n Mensaje: " + e.getMessage());
        }
    }

    public Boolean checkLicencia(Licencia licencia) throws RuntimeException{
        if (licencia.getCreatedBy() == null) {
            throw new RuntimeException("Debe ingresar el creador");
        } else if (licencia.getDocumentation() == null) {
            throw new RuntimeException("Debe ingresar la documentación");
        } else if (licencia.getEndDate() == null) {
            throw new RuntimeException("Debe ingresar la fecha de finalización");
        } else if (licencia.getStartDate() == null) {
            throw new RuntimeException("Debe ingresar la fecha de inicio");
        } else if (licencia.getUpdatedBy() == null) {
            throw new RuntimeException("Debe ingresar el actualizador");
        } else {
            return true;
        }
    };
}