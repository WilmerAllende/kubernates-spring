package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.validator;

import com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.service.UsuarioService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsByEmailValidation implements ConstraintValidator<ExistByEmail,String> {

    @Autowired
    private UsuarioService service;

    @Autowired
    private RequestContextHolderHttpValidation requestContextHolder;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(service == null) {
            return  true;
        }
        if (!requestContextHolder.isPostRequest()) {
            return true; // No validar si no es POST
        }
        return  !service.existsByEmail(s);
    }
}
