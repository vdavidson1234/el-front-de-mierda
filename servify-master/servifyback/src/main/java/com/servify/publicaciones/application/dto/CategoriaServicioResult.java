package com.servify.publicaciones.application.dto;

import com.servify.publicaciones.domain.enumtype.EstadoCategoria;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de salida para exponer datos de una categoria de servicio.
 */
public class CategoriaServicioResult {

    private UUID id;
    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaModificacion;

    private CategoriaServicioResult() {
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoCategoria getEstado() {
        return estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final CategoriaServicioResult instance;

        public Builder() {
            this.instance = new CategoriaServicioResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            instance.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            instance.descripcion = descripcion;
            return this;
        }

        public Builder estado(EstadoCategoria estado) {
            instance.estado = estado;
            return this;
        }

        public Builder fechaCreacion(LocalDateTime fechaCreacion) {
            instance.fechaCreacion = fechaCreacion;
            return this;
        }

        public Builder fechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
            instance.fechaUltimaModificacion = fechaUltimaModificacion;
            return this;
        }

        public CategoriaServicioResult build() {
            return instance;
        }
    }
}
