package com.wilmer.springcloud.mscv.cursos.msvc_cursos.exception;

public class UserErrorFoundException extends RuntimeException {
    public UserErrorFoundException(String message) {
        super(message);
    }
}
