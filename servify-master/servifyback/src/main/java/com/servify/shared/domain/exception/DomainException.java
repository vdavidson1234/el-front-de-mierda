package com.servify.shared.domain.exception;


///excepcion base para errores del dominio, como violaciones de reglas de negocio o errores de validacion
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}