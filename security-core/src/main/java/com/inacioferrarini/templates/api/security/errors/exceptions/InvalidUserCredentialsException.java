package com.inacioferrarini.templates.api.security.errors.exceptions;

import java.util.ResourceBundle;

public class InvalidUserCredentialsException extends RuntimeException {

    private static final ResourceBundle resource = ResourceBundle.getBundle("ErrorMessages");
    private static final String ERROR_MESSAGE_KEY = "login.invalidcredentials.message";

    public String getErrorMessage() {
        return resource.getString(ERROR_MESSAGE_KEY);
    }

}
