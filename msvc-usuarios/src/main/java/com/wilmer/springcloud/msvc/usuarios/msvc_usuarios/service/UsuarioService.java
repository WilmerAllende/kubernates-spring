package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.service;

import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    Optional<Usuario> editar(Usuario usuario, Long id);
    void Eliminar(Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Usuario> findAllById(Iterable<Long> ids);
}
