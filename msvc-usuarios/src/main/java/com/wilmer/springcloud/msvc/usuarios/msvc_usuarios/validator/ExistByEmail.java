package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByEmailValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByEmail {
    String message() default "ya existe en la base de datos ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
