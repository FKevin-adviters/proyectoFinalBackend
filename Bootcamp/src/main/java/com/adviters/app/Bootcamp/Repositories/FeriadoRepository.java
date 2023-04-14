package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Feriados.Feriado;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeriadoRepository extends JpaRepository<Feriado, UUID> {

}
