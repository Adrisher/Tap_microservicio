package com.msvusuarios.msvusuarios.repository;

import com.msvusuarios.msvusuarios.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {

    Optional<Usuario> findByEmail(String email);
}
