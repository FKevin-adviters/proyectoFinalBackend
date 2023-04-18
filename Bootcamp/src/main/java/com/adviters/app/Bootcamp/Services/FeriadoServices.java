package com.adviters.app.Bootcamp.Services;

import com.adviters.app.Bootcamp.Models.Feriados.Feriado;
import com.adviters.app.Bootcamp.Repositories.FeriadoRepository;
import com.adviters.app.Bootcamp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeriadoServices {

    @Autowired
    private FeriadoRepository feriadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Feriado createFeriado(Feriado feriado) {
        feriado.setCreatedBy(usuarioRepository.getOne(feriado.getCreatedBy().getId()));
        feriado.setCreatedDate(new Date());
        return feriadoRepository.save(feriado);
    }

    public Feriado getFeriadoById(UUID id) {
        Optional<Feriado> optionalFeriado = feriadoRepository.findById(id);
        if (optionalFeriado.isPresent()) {
            return optionalFeriado.get();
        } else {
            throw new NoSuchElementException("No existe un feriado con el id " + id);
        }
    }

    public List<Feriado> getAllFeriados() {
        return feriadoRepository.findAll();
    }

    public Feriado updateFeriado(UUID id, Feriado feriado) {
        Optional<Feriado> feriadoData = feriadoRepository.findById(id);
        if (feriadoData.isPresent()) {
            Feriado _feriado = feriadoData.get();
            _feriado.setDescription(feriado.getDescription());
            _feriado.setDate(feriado.getDate());
            _feriado.setUpdatedBy(usuarioRepository.getOne(feriado.getUpdatedBy().getId()));
            _feriado.setUpdatedDate(new Date());
            return feriadoRepository.save(_feriado);
        } else {
            return null;
        }
    }

    public void deleteFeriado(UUID id) {
        Optional<Feriado> feriadoData = feriadoRepository.findById(id);
        if (feriadoData.isPresent()) {
            Feriado _feriado = feriadoData.get();
            _feriado.setDeleted(true);
            _feriado.setUpdatedBy(usuarioRepository.getOne(_feriado.getUpdatedBy().getId()));
            _feriado.setUpdatedDate(new Date());
            feriadoRepository.save(_feriado);
        }
    }
}
