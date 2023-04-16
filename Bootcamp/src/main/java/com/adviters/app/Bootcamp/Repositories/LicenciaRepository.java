package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Long> {


    Optional<Licencia> findById(Long id);

    List<Licencia> findByUsuarioId(Long idUsuario);
}