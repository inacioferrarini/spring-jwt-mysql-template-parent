package com.inacioferrarini.templates.api.security.components;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticatedUser {

    UserDTO getRequestAuthor();

}
