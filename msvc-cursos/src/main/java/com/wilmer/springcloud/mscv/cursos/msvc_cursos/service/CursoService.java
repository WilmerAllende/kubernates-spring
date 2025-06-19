package com.wilmer.springcloud.mscv.cursos.msvc_cursos.service;

import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.Usuario;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso);
    Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso);
    Optional<Usuario> eliminarUsuarioDelCurso(Usuario usuario, Long idCurso);

    Optional<Curso> porIdconUsuarios(Long id);
    void eliminarCursoUsuarioPorId(Long id);

}
