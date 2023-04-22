package com.msvusuarios.msvusuarios.controller;

import com.msvusuarios.msvusuarios.models.Usuario;
import com.msvusuarios.msvusuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {


    @Autowired
    private UsuarioService service;

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping
    public List<Usuario> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuarioOptional=service.porId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        if(service.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.
                    singletonMap("Message", "Ya existe el correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, @PathVariable Long id){
        if(bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        Optional<Usuario> usuarioOptional=service.porId(id);
        if (usuarioOptional.isPresent()){
            Usuario usuarioDb=usuarioOptional.get();

            if(service.findByEmail(usuario.getEmail()).isPresent()
            && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail())){
                return ResponseEntity.badRequest().body(Collections.
                        singletonMap("Message", "Ya existe el correo"));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional=service.porId(id);
        if (usuarioOptional.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

