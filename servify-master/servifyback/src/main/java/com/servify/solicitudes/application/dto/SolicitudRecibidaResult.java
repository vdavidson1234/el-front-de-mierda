package com.servify.solicitudes.application.dto;

import com.servify.shared.domain.enumtype.ModalidadServicio;
import com.servify.solicitudes.domain.enumtype.EstadoDistribucion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class SolicitudRecibidaResult {

    private UUID distribucionSolicitudId;
    private UUID solicitudId;
    private UUID publicacionServicioId;
    private UUID prestadorId;
    private UUID solicitanteId;
    private UUID categoriaServicioId;
    private ModalidadServicio modalidadServicio;
    private UbicacionSolicitudResult ubicacion;
    private DisponibilidadHorariaResult disponibilidadRequerida;
    private String descripcionNecesidad;
    private BigDecimal precioReferencia;
    private EstadoDistribucion estadoDistribucion;
    private Integer rondaDistribucion;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaExpiracion;

    private SolicitudRecibidaResult() {
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getPublicacionServicioId() {
        return publicacionServicioId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }

    public UUID getCategoriaServicioId() {
        return categoriaServicioId;
    }

    public ModalidadServicio getModalidadServicio() {
        return modalidadServicio;
    }

    public UbicacionSolicitudResult getUbicacion() {
        return ubicacion;
    }

    public DisponibilidadHorariaResult getDisponibilidadRequerida() {
        return disponibilidadRequerida;
    }

    public String getDescripcionNecesidad() {
        return descripcionNecesidad;
    }

    public BigDecimal getPrecioReferencia() {
        return precioReferencia;
    }

    public EstadoDistribucion getEstadoDistribucion() {
        return estadoDistribucion;
    }

    public Integer getRondaDistribucion() {
        return rondaDistribucion;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final SolicitudRecibidaResult instance;

        public Builder() {
            this.instance = new SolicitudRecibidaResult();
        }

        public Builder distribucionSolicitudId(UUID distribucionSolicitudId) {
            instance.distribucionSolicitudId = distribucionSolicitudId;
            return this;
        }

        public Builder solicitudId(UUID solicitudId) {
            instance.solicitudId = solicitudId;
            return this;
        }

        public Builder publicacionServicioId(UUID publicacionServicioId) {
            instance.publicacionServicioId = publicacionServicioId;
            return this;
        }

        public Builder prestadorId(UUID prestadorId) {
            instance.prestadorId = prestadorId;
            return this;
        }

        public Builder solicitanteId(UUID solicitanteId) {
            instance.solicitanteId = solicitanteId;
            return this;
        }

        public Builder categoriaServicioId(UUID categoriaServicioId) {
            instance.categoriaServicioId = categoriaServicioId;
            return this;
        }

        public Builder modalidadServicio(ModalidadServicio modalidadServicio) {
            instance.modalidadServicio = modalidadServicio;
            return this;
        }

        public Builder ubicacion(UbicacionSolicitudResult ubicacion) {
            instance.ubicacion = ubicacion;
            return this;
        }

        public Builder disponibilidadRequerida(DisponibilidadHorariaResult disponibilidadRequerida) {
            instance.disponibilidadRequerida = disponibilidadRequerida;
            return this;
        }

        public Builder descripcionNecesidad(String descripcionNecesidad) {
            instance.descripcionNecesidad = descripcionNecesidad;
            return this;
        }

        public Builder precioReferencia(BigDecimal precioReferencia) {
            instance.precioReferencia = precioReferencia;
            return this;
        }

        public Builder estadoDistribucion(EstadoDistribucion estadoDistribucion) {
            instance.estadoDistribucion = estadoDistribucion;
            return this;
        }

        public Builder rondaDistribucion(Integer rondaDistribucion) {
            instance.rondaDistribucion = rondaDistribucion;
            return this;
        }

        public Builder fechaEnvio(LocalDateTime fechaEnvio) {
            instance.fechaEnvio = fechaEnvio;
            return this;
        }

        public Builder fechaExpiracion(LocalDateTime fechaExpiracion) {
            instance.fechaExpiracion = fechaExpiracion;
            return this;
        }

        public SolicitudRecibidaResult build() {
            return instance;
        }
    }
}
