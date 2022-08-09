package com.inacioferrarini.templates.api.errors.advices;

import com.inacioferrarini.templates.api.base.models.dtos.StringErrorResponseRecord;
import com.inacioferrarini.templates.api.base.models.dtos.StringListErrorResponseRecord;
import com.inacioferrarini.templates.api.security.errors.exceptions.FieldValueAlreadyInUseException;
import com.inacioferrarini.templates.api.security.errors.exceptions.InvalidUserCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(x -> x.getDefaultMessage())
                                .collect(Collectors.toList());

        StringListErrorResponseRecord errorResponse = new StringListErrorResponseRecord(
                LocalDateTime.now(),
                status.value(),
                errors
        );

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<StringErrorResponseRecord> handleInvalidUserCredentialsException(
            InvalidUserCredentialsException ex,
            WebRequest request
    ) {
        StringErrorResponseRecord errorResponse = new StringErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getErrorMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FieldValueAlreadyInUseException.class)
    public ResponseEntity<StringErrorResponseRecord> handleFieldValueAlreadyInUseException(
            FieldValueAlreadyInUseException ex,
            WebRequest request
    ) {
        StringErrorResponseRecord errorResponse = new StringErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getField().getValidationMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
