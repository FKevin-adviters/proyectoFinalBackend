package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;

import com.adviters.app.Bootcamp.dtos.Licencias.LicenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LicenciaServices {
    @Autowired
    private LicenciaRepository repository;

    @Autowired
    private EntityManager entityManager;

    public List<LicenciaDTO> getLicenciasByUserId(UUID id) throws Exception {
        Query query = entityManager.createQuery("SELECT e FROM Licencia e JOIN FETCH e.usuario user WHERE user.id = :userId", Licencia.class);
        query.setParameter("userId", id);
        List<Licencia> licenciaList = query.getResultList();
        List<LicenciaDTO> dtoList= new ArrayList<>();
        if(licenciaList.isEmpty()){
           throw new Exception("No se ha logrado encontrar las licencias para este user.");
        }
        for (Licencia licencia:
             licenciaList) {
            LicenciaDTO dto = new LicenciaDTO();
            dto.setLicenseId(licencia.getLicenseId());
            dto.setLicenseTypeId(licencia.getTipoLicencia().getLicenseId());
            dto.setStatus(licencia.getEstadoLicencia().getDescription());
            dto.setStartDate(licencia.getStartDate());
            dto.setEndDate(licencia.getEndDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public LicenciaDTO getOneLicenseByUserAndLicenseID(UUID userId, Long licenseId) throws Exception {
        Query query = entityManager.createQuery("SELECT e FROM Licencia e JOIN FETCH e.usuario user WHERE user.id = :userId AND e.id = :licenseId", Licencia.class);
        query.setParameter("userId", userId);
        query.setParameter("licenseId", licenseId);
        Licencia licencia = (Licencia) query.getSingleResult();
        if(licencia == null) {
            throw new Exception("No se ha encontrado la licencia");
        }
        LicenciaDTO dto = new LicenciaDTO();
        dto.setEndDate(licencia.getEndDate());
        dto.setStartDate(licencia.getStartDate());
        dto.setStatus(licencia.getEstadoLicencia().getDescription());
        dto.setLicenseId(licencia.getLicenseId());
        dto.setLicenseTypeId(licencia.getTipoLicencia().getLicenseId());
        return dto;
    }
}