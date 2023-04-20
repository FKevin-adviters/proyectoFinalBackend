package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Licencias.EstadoLicencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenciaEstadoRepository extends JpaRepository<EstadoLicencia, Long> {
}
