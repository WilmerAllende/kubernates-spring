package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.service;

import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.clients.CursoClienteRest;
import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.models.entity.Usuario;
import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClienteRest cursoClienteRest;

    @Transactional( readOnly = true )
    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Transactional( readOnly = true )
    @Override
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Optional<Usuario> editar(Usuario usuario, Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()){
            Usuario usuarioDb = usuarioOptional.orElseThrow();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setPassword(usuario.getPassword());
            return Optional.of(usuarioRepository.save(usuarioDb));
        }
        return usuarioOptional;
    }

    @Transactional
    @Override
    public void Eliminar(Long id) {

        usuarioRepository.deleteById(id);
        cursoClienteRest.eliminarCursoUsuarioPorId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAllById(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }
}
