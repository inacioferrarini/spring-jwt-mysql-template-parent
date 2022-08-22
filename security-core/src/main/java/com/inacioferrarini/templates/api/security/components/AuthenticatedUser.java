package com.inacioferrarini.templates.api.security.components;

import com.inacioferrarini.templates.api.security.models.dtos.UserDTO;

public interface AuthenticatedUser {

    UserDTO getRequestAuthor();

}
