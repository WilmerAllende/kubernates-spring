package com.wilmer.springcloud.msvc.usuarios.msvc_usuarios.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestContextHolderHttpValidation {
    public boolean isPostRequest() {
        var attributes = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes servletAttributes) {
            return "POST".equalsIgnoreCase(servletAttributes.getRequest().getMethod());
        }
        return false;
    }
}
