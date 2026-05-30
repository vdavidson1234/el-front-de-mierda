package com.servify.solicitudes.application.dto;

import com.servify.solicitudes.domain.enumtype.EstadoAsignacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AsignacionServicioResult {

    private UUID id;
    private UUID solicitudId;
    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private UUID publicacionServicioId;
    private BigDecimal precioAcordado;
    private EstadoAsignacion estado;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinalizacion;

    private AsignacionServicioResult() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public UUID getPublicacionServicioId() {
        return publicacionServicioId;
    }

    public BigDecimal getPrecioAcordado() {
        return precioAcordado;
    }

    public EstadoAsignacion getEstado() {
        return estado;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AsignacionServicioResult instance;

        public Builder() {
            this.instance = new AsignacionServicioResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder solicitudId(UUID solicitudId) {
            instance.solicitudId = solicitudId;
            return this;
        }

        public Builder distribucionSolicitudId(UUID distribucionSolicitudId) {
            instance.distribucionSolicitudId = distribucionSolicitudId;
            return this;
        }

        public Builder prestadorId(UUID prestadorId) {
            instance.prestadorId = prestadorId;
            return this;
        }

        public Builder publicacionServicioId(UUID publicacionServicioId) {
            instance.publicacionServicioId = publicacionServicioId;
            return this;
        }

        public Builder precioAcordado(BigDecimal precioAcordado) {
            instance.precioAcordado = precioAcordado;
            return this;
        }

        public Builder estado(EstadoAsignacion estado) {
            instance.estado = estado;
            return this;
        }

        public Builder fechaAsignacion(LocalDateTime fechaAsignacion) {
            instance.fechaAsignacion = fechaAsignacion;
            return this;
        }

        public Builder fechaFinalizacion(LocalDateTime fechaFinalizacion) {
            instance.fechaFinalizacion = fechaFinalizacion;
            return this;
        }

        public AsignacionServicioResult build() {
            return instance;
        }
    }
}
