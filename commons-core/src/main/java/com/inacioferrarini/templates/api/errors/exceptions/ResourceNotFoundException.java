package com.inacioferrarini.templates.api.errors.exceptions;

import java.util.ResourceBundle;

public class ResourceNotFoundException extends RuntimeException {

    private static final ResourceBundle resource = ResourceBundle.getBundle("commons-core-error-messages");
    private static final String ERROR_MESSAGE_KEY = "resource.not_found.message";

    public String getErrorMessage() {
        return resource.getString(ERROR_MESSAGE_KEY);
    }

}
