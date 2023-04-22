package com.msvusuarios.msvusuarios.services;

import com.msvusuarios.msvusuarios.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    Optional<Usuario> findByEmail (String email);
}
