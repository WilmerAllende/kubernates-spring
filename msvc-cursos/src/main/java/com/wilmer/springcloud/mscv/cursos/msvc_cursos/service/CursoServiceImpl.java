package com.wilmer.springcloud.mscv.cursos.msvc_cursos.service;

import com.wilmer.springcloud.mscv.cursos.msvc_cursos.clients.UsuarioClientRest;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.Usuario;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.entity.Curso;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.entity.CursoUsuario;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest clientRest;

    @Transactional(readOnly = true)
    @Override
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = repository.findById(idCurso);
        if (o.isPresent()){
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());
            Curso curso = o.orElseThrow();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = repository.findById(idCurso);
        if (o.isPresent()){
            Usuario usuarioNuevoMsvc = clientRest.crear(usuario);
            Curso curso = o.orElseThrow();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuarioDelCurso(Usuario usuario, Long idCurso) {
        Optional<Curso> o = repository.findById(idCurso);
        if (o.isPresent()){
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());
            Curso curso = o.orElseThrow();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Curso> porIdconUsuarios(Long id) {
        Optional<Curso> o = repository.findById(id);
        if (o.isPresent()){
            Curso curso = o.orElseThrow();
            if (!curso.getCursoUsuarios().isEmpty()){
                List<Long> usersId = curso.getCursoUsuarios().stream().map( cursoUsuario ->  cursoUsuario.getUsuarioId()
                 ).collect(Collectors.toList());
                List<Usuario> usuarios = clientRest.obtenerAlumnosCurso(usersId);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }
}
