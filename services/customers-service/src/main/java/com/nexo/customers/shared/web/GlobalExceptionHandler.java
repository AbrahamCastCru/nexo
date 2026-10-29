package com.nexo.customers.shared.web;

import java.net.URI;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nexo.customers.customer.CustomerNotFoundException;
import com.nexo.customers.customer.DuplicateCustomerException;

@RestControllerAdvice 
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    ProblemDetail handleCustomerNotFound(CustomerNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, 
            ex.getMessage()
        );
        problem.setTitle("Customer not found");
        problem.setType(URI.create("https://nexo.dev/problems/customer-not-found"));
        return problem;
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    ProblemDetail handleDuplicateCustomer(DuplicateCustomerException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT, 
            ex.getMessage()
        );
        problem.setTitle("Customer already exists");
        problem.setType(URI.create("https://nexo.dev/problems/duplicate-customer"));
        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleDataIntegrityViolation() {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT, 
            "The operation could not be completed due to a data integrity violation. Please ensure fields are not duplicated or missing required values."
        );
        problem.setTitle("Customer already exists");
        problem.setType(URI.create("https://nexo.dev/problems/duplicate-customer"));
        return problem;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception ex) {
        log.error("Unexpected error caught by global handler: ", ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "Unexpected error."
        );
        problem.setTitle("Internal Server Error");
        return problem;
    }

    private record ValidationError(String field, String message) {}
    
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        ProblemDetail problem = ex.getBody();
        problem.setTitle("Invalid request");
        problem.setDetail("One or more fields are invalid.");
        problem.setType(URI.create("https://nexo.dev/problems/validation-error"));

        java.util.List<ValidationError> errList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof org.springframework.validation.FieldError fieldError) {
                        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new ValidationError(error.getObjectName(), error.getDefaultMessage());
                }).toList();
        problem.setProperty("errors", errList);
        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

}
