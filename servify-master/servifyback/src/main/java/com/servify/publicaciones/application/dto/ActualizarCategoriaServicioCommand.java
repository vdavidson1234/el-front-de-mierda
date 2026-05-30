package com.servify.publicaciones.application.dto;

import java.util.UUID;

/**
 * DTO de comando para actualizar una categoria de servicio.
 */
public class ActualizarCategoriaServicioCommand {

    private UUID categoriaServicioId;
    private String nombre;
    private String descripcion;

    public ActualizarCategoriaServicioCommand() {
    }

    public ActualizarCategoriaServicioCommand(UUID categoriaServicioId,
                                              String nombre,
                                              String descripcion) {
        this.categoriaServicioId = categoriaServicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public UUID getCategoriaServicioId() {
        return categoriaServicioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
