package com.adviters.app.Bootcamp.Repositories;

import com.adviters.app.Bootcamp.Models.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/*Esta anotación @Query se utiliza en JPA (Java Persistence API) para
definir una consulta personalizada en JPQL (Java Persistence Query Language).

La consulta selecciona un objeto Usuario y utiliza la cláusula JOIN FETCH
para cargar los objetos Role relacionados con el usuario en una sola consulta
en lugar de realizar consultas adicionales para cada uno de ellos.

La condición de la cláusula WHERE de la consulta es que el correo electrónico
del Usuario sea igual al valor pasado como parámetro ":email".

La anotación @Param se utiliza para vincular el valor del parámetro "email"
de la firma del método con el valor proporcionado como parámetro en la consulta
JPQL ":email".

El método findByEmail devuelve un objeto Usuario que coincide con la condición
de la consulta, es decir, un Usuario con el correo electrónico especificado como parámetro.

En resumen, este método busca y devuelve un objeto Usuario con los objetos Role
relacionados con él cargados en una sola consulta, filtrando los resultados por el
correo electrónico proporcionado como parámetro.*/
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Query("SELECT e FROM Usuario e JOIN FETCH e.roles WHERE e.email= (:email)")
    Usuario findByEmail(@Param("email") String email);
    @Query("SELECT e FROM Usuario e JOIN FETCH e.roles WHERE e.email= (:email)")
    Usuario findByRol(@Param("rol") String rol);

}
