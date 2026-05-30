package com.servify.publicaciones.application.dto;

import com.servify.publicaciones.domain.enumtype.EstadoPublicacion;
import com.servify.shared.domain.enumtype.ModalidadServicio;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de salida para una publicacion compatible con un criterio de busqueda.
 */
public class PublicacionCompatibleResult {

    private UUID publicacionServicioId;
    private UUID prestadorId;
    private UUID categoriaServicioId;
    private String titulo;
    private ModalidadServicio modalidadServicio;
    private BigDecimal precioBase;
    private EstadoPublicacion estado;
    private Double distanciaKm;

    private PublicacionCompatibleResult() {
    }

    public UUID getPublicacionServicioId() {
        return publicacionServicioId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public UUID getCategoriaServicioId() {
        return categoriaServicioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public ModalidadServicio getModalidadServicio() {
        return modalidadServicio;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PublicacionCompatibleResult instance;

        public Builder() {
            this.instance = new PublicacionCompatibleResult();
        }

        public Builder publicacionServicioId(UUID publicacionServicioId) {
            instance.publicacionServicioId = publicacionServicioId;
            return this;
        }

        public Builder prestadorId(UUID prestadorId) {
            instance.prestadorId = prestadorId;
            return this;
        }

        public Builder categoriaServicioId(UUID categoriaServicioId) {
            instance.categoriaServicioId = categoriaServicioId;
            return this;
        }

        public Builder titulo(String titulo) {
            instance.titulo = titulo;
            return this;
        }

        public Builder modalidadServicio(ModalidadServicio modalidadServicio) {
            instance.modalidadServicio = modalidadServicio;
            return this;
        }

        public Builder precioBase(BigDecimal precioBase) {
            instance.precioBase = precioBase;
            return this;
        }

        public Builder estado(EstadoPublicacion estado) {
            instance.estado = estado;
            return this;
        }

        public Builder distanciaKm(Double distanciaKm) {
            instance.distanciaKm = distanciaKm;
            return this;
        }

        public PublicacionCompatibleResult build() {
            return instance;
        }
    }
}
