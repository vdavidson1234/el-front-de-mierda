package com.servify.usuarios.domain.valueobject;

import java.util.Objects;

/**
 * Value Object que encapsula datos de contacto (email y teléfono).
 */
public class Contacto {

    private String email;
    private String telefono;

    /**
     * Constructor protegido sin argumentos.
     */
    protected Contacto() {
    }

    /**
     * Crea un contacto con email y teléfono.
     */
    public Contacto(String email, String telefono) {
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Retorna el email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna el teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Verifica si hay teléfono registrado.
     */
    public boolean tieneTelefono() {
        return telefono != null && !telefono.isBlank();
    }

    /**
     * Valida que el email tenga formato básico válido:
     * no nulo, no vacío, contiene @ y no comienza ni termina con @.
     */
    public boolean emailValido() {
        return email != null
                && !email.isBlank()
                && email.contains("@")
                && !email.startsWith("@")
                && !email.endsWith("@");
    }

    /**
     * Compara por igualdad de email y teléfono.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contacto contacto)) {
            return false;
        }
        return Objects.equals(email, contacto.email)
                && Objects.equals(telefono, contacto.telefono);
    }

    /**
     * Hash basado en email y teléfono.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, telefono);
    }
}