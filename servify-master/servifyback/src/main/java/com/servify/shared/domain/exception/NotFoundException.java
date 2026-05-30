package com.servify.shared.domain.exception;


///excepcion que se usa cuando no se encuentra un recurso o entidad, como un usuario o producto
public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}