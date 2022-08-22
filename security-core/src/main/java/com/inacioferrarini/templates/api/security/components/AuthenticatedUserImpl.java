package com.inacioferrarini.templates.api.security.components;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserImpl implements AuthenticatedUser {

    public UserDTO getRequestAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDTO) authentication.getPrincipal();
    }

}
