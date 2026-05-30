package com.servify.shared.domain.exception;


///excepcion que se usa cuando se viola una regla de validacion, como por ejemplo, un email no valido, o una contraseña que no cumple con los requisitos de seguridad
public class ValidationException extends DomainException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
