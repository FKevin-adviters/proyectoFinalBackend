package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Licencias.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, UUID> {

}
