package com.inacioferrarini.templates.api.security.errors.exceptions;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class FieldValueAlreadyInUseException extends RuntimeException {

    private static final ResourceBundle resource = ResourceBundle.getBundle("ValidationMessages");
    private static final String ERROR_MESSAGE_KEY = "constraints.uniqueValue.uniqueValueViolation";
    private final Field field;

    public FieldValueAlreadyInUseException(final Field field) {
        this.field = field;
    }

    public enum Field {
        USERNAME("Username"),
        EMAIL("Email");

        private final String fieldName;

        Field(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getValidationMessage() {
            return MessageFormat.format(
                    resource.getString(ERROR_MESSAGE_KEY),
                    getFieldName()
            );
        }

    }

    public Field getField() {
        return this.field;
    }

}
