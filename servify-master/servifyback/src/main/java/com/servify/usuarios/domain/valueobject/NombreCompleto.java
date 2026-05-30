package com.servify.usuarios.domain.valueobject;

import java.util.Objects;

/**
 * Value Object que representa el nombre completo de una persona.
 * Encapsula nombre y apellido en una sola unidad de dominio.
 */
public class NombreCompleto {

    private String nombre;
    private String apellido;

    /**
     * Constructor protegido sin argumentos.
     * Lo requieren algunos frameworks de persistencia/serializacion.
     */
    protected NombreCompleto() {
    }

    /**
     * Crea un nombre completo con nombre y apellido.
     * @param nombre nombre de la persona
     * @param apellido apellido de la persona
     */
    public NombreCompleto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Devuelve el nombre.

     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el apellido.

     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Arma un texto listo para mostrar en UI.
     * Si falta una de las partes, retorna la disponible sin generar espacios extra.

     * @return nombre y apellido concatenados de forma segura
     */
    public String nombreMostrar() {
        return (nombre != null ? nombre : "") +
                ((nombre != null && apellido != null) ? " " : "") +
                (apellido != null ? apellido : "");
    }

    /**
     * Indica si el objeto tiene datos minimos validos.
     * Requiere nombre y apellido no nulos ni vacios.
     * @return true si ambos campos estan completos
     */
    public boolean esValido() {
        return nombre != null && !nombre.isBlank()
                && apellido != null && !apellido.isBlank();
    }

    /**
     * Compara igualdad por valor.
     * Dos instancias son iguales si tienen el mismo nombre y apellido.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NombreCompleto that)) {
            return false;
        }
        return Objects.equals(nombre, that.nombre)
                && Objects.equals(apellido, that.apellido);
    }

    /**
     * Genera hash consistente con equals.
     * Permite usar este Value Object en colecciones hash (HashSet/HashMap).
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido);
    }
}