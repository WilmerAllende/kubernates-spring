package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "msvc-cursos", url = "localhost:8002") // Este es por ejecuta local
@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8002") // este invoca al mscv interno de cursp , es decir curso no esta dockerizado
public interface CursoClienteRest {
    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
