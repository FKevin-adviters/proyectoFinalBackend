package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Feriados.Feriado;
import com.adviters.app.Bootcamp.Models.Licencias.EstadoLicencia;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import com.adviters.app.Bootcamp.Repositories.LicenciaEstadoRepository;
import com.adviters.app.Bootcamp.Repositories.LicenciaRepository;

import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import com.adviters.app.Bootcamp.dtos.Licencias.LicenciaDTO;
import com.adviters.app.Bootcamp.dtos.UsuarioDTOS.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
@Transactional
public class LicenciaServices {
    @Autowired
    private LicenciaRepository repository;

    @Autowired
    private LicenciaEstadoRepository licenciaEstadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioServices usuarioServices;

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
            UsuarioDTO userDto = usuarioServices.createUsuarioDTO(licencia.getUsuario());
            dto.setUsuarioDTO(userDto);
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

    public List<LicenciaDTO> getLicenciasDTOByStateId(Long idState) throws Exception{
        EstadoLicencia estadoLicencia = licenciaEstadoRepository.findById(idState).get();
        if(estadoLicencia != null){
            Query query = entityManager.createQuery("SELECT e FROM Licencia e JOIN FETCH e.estadoLicencia state WHERE state.id = :idState", Licencia.class);
            query.setParameter("idState", idState);
            List<Licencia> licenciaList = query.getResultList();
            List<LicenciaDTO> licenciaDTOS = new ArrayList<>();
            if(licenciaList.isEmpty()) {
                return licenciaDTOS;
            }
            for (Licencia licencia :
                    licenciaList) {
                LicenciaDTO dto = new LicenciaDTO();
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                //usuario dto
                usuarioDTO.setId(licencia.getUsuario().getId());
                usuarioDTO.setName(licencia.getUsuario().getName());
                usuarioDTO.setLastname(licencia.getUsuario().getLastname());
                usuarioDTO.setProfile_picture(licencia.getUsuario().getProfile_picture());
                //licencia dto
                dto.setLicenseId(licencia.getLicenseId());
                dto.setStatus(licencia.getEstadoLicencia().getDescription());
                dto.setEndDate(licencia.getEndDate());
                dto.setStartDate(licencia.getStartDate());
                dto.setLicenseTypeId(licencia.getTipoLicencia().getLicenseId());
                dto.setUsuarioDTO(usuarioDTO);
                licenciaDTOS.add(dto);
            }
            return licenciaDTOS;
        }
        List<LicenciaDTO> list = new ArrayList<>();
        return list;
    };


    public List<LicenciaDTO> getLicenciasDTOByStateIdAndUserId(Long idState, UUID userId) throws Exception{
        EstadoLicencia estadoLicencia = licenciaEstadoRepository.findById(idState).get();
        if(estadoLicencia != null){
            Query query = entityManager.createQuery("SELECT e FROM Licencia e JOIN FETCH e.estadoLicencia state WHERE state.id = :idState", Licencia.class);
            query.setParameter("idState", idState);
            List<Licencia> licenciaList = query.getResultList();
            List<LicenciaDTO> licenciaDTOS = new ArrayList<>();
            if(licenciaList.isEmpty()) {
                throw new Exception("No se han encontrado licencias para el idState: " + idState);
            }
            for (Licencia licencia :
                    licenciaList) {
                if(licencia.getUsuario().getId() != null && licencia.getUsuario().getId() == userId) {
                    LicenciaDTO dto = new LicenciaDTO();
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    //usuario dto
                    usuarioDTO.setId(licencia.getUsuario().getId());
                    usuarioDTO.setName(licencia.getUsuario().getName());
                    usuarioDTO.setLastname(licencia.getUsuario().getLastname());
                    usuarioDTO.setProfile_picture(licencia.getUsuario().getProfile_picture());
                    //licencia dto
                    dto.setLicenseId(licencia.getLicenseId());
                    dto.setStatus(licencia.getEstadoLicencia().getDescription());
                    dto.setEndDate(licencia.getEndDate());
                    dto.setStartDate(licencia.getStartDate());
                    dto.setLicenseTypeId(licencia.getTipoLicencia().getLicenseId());
                    dto.setUsuarioDTO(usuarioDTO);
                    licenciaDTOS.add(dto);
                }
            }
            if(licenciaDTOS.isEmpty()){
                throw new Exception("No se han encontrado licencias para el usuario con el estado asignado");
            }
            return licenciaDTOS;
        }
        throw new Exception("No se ha encontrado el estado: " + idState);
    };
    public void deleteLicencia(Long id) {
        Optional<Licencia> licenciaData = repository.findById(id);
        if (licenciaData.isPresent()) {
            Licencia _licencia = licenciaData.get();
            _licencia.setDeleted(true);
            repository.save(_licencia);
        }
    }
}