package com.wilmer.springcloud.mscv.cursos.msvc_cursos.controller;


import com.wilmer.springcloud.mscv.cursos.msvc_cursos.exception.UserErrorFoundException;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.Usuario;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.entity.Curso;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.service.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> listar(){
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id ){
        Optional<Curso> CursoOptional = cursoService.porIdconUsuarios(id);
        if (CursoOptional.isPresent()){
            return ResponseEntity.ok(CursoOptional.orElseThrow());

        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso Curso, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( cursoService.guardar(Curso) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso Curso, BindingResult result, @PathVariable Long id){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<Curso> CursoOptional = cursoService.porId(id);
        if (CursoOptional.isPresent()){
            Curso CursoDB = CursoOptional.orElseThrow();
            CursoDB.setNombre(Curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(CursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> CursoOptional = cursoService.porId(id);
        if (CursoOptional.isPresent()){
            cursoService.eliminar(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(CursoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOpt ;
        try {
            usuarioOpt = cursoService.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap( "mensaje", "No existe el usuario por el id o erroro en la comunicacción " +
                    e.getMessage()));
        }


        if (usuarioOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOpt ;
        try {
            usuarioOpt = cursoService.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
            throw new UserErrorFoundException("No se pudo crear el usuario o error en la comunicación: " + e.getMessage());

           // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap( "mensaje", "No se pudo crear el usuario o error en la comunicación " +
             //       e.getMessage()));
        }


        if (usuarioOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOpt ;
        try {
            usuarioOpt = cursoService.eliminarUsuarioDelCurso(usuario, cursoId);
        }catch (FeignException e){
            throw new UserErrorFoundException("No existe usuario por Id o error en la comunicación : " + e.getMessage());
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap( "mensaje", "No existe usuario por Id o error en la comunicación " +
              //      e.getMessage()));
        }


        if (usuarioOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach( fieldError -> {
            errors.put( fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage()) ;
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        cursoService.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

}
