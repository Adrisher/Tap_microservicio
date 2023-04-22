package com.msvusuarios.msvusuarios.services;

import com.msvusuarios.msvusuarios.models.Usuario;
import com.msvusuarios.msvusuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll() ;
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
