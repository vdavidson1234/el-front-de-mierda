package com.servify.shared.domain.exception;


///excepcion que se usa cuando se viola una regla de negocio
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}