package com.nexo.customers.customer;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
     // Recibe exactamente el UUID que se buscó y no se encontró
    public CustomerNotFoundException(UUID id) {
        // Le pasamos el mensaje armado a la clase padre (RuntimeException)
        super("Customer with id " + id + " was not found.");
    }
}
