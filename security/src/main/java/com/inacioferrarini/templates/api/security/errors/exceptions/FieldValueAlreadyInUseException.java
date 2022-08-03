package com.inacioferrarini.templates.api.security.errors.exceptions;

public class FieldValueAlreadyInUseException extends RuntimeException {

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

    }

    public Field getField() {
        return this.field;
    }

}
