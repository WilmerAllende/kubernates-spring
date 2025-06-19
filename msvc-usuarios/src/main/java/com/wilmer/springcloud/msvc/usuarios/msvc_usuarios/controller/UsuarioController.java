package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.controller;


import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.models.entity.Usuario;
import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Map<String, List<Usuario>> listar(){

        return Collections.singletonMap("usuarios", usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id ){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.orElseThrow());

        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        if(!usuario.getEmail().isEmpty() && usuarioService.existsByEmail(usuario.getEmail())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con este correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( usuarioService.guardar(usuario) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<Usuario> usuarioOptional = usuarioService.editar(usuario, id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()){
            usuarioService.Eliminar(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosCurso( @RequestParam List<Long> ids ){
        return ResponseEntity.ok(usuarioService.findAllById(ids));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach( fieldError -> {
            errors.put( fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage()) ;
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
