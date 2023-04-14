package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LicenciaRepository extends JpaRepository<Licencia, UUID> {

}
