package com.wilmer.springcloud.mscv.cursos.msvc_cursos.controller;


import com.wilmer.springcloud.mscv.cursos.msvc_cursos.exception.UserErrorFoundException;
import com.wilmer.springcloud.mscv.cursos.msvc_cursos.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler( UserErrorFoundException.class )
    public ResponseEntity<Error> userErrorFoundException(UserErrorFoundException ex){
        Error error = new Error();
        error.setError(ex.getMessage());
        error.setMensaje("Hubo un error en el procesamiento del usuario. Comunicarse con el administrador del Sistema");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setFecha(new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
