package com.servify.solicitudes.application.dto;

import com.servify.solicitudes.domain.enumtype.EstadoContraoferta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ContraofertaResult {

    private UUID id;
    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private BigDecimal precioOriginal;
    private BigDecimal precioPropuesto;
    private String mensaje;
    private EstadoContraoferta estado;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaResolucion;

    private ContraofertaResult() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public BigDecimal getPrecioOriginal() {
        return precioOriginal;
    }

    public BigDecimal getPrecioPropuesto() {
        return precioPropuesto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public EstadoContraoferta getEstado() {
        return estado;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ContraofertaResult instance;

        public Builder() {
            this.instance = new ContraofertaResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
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

        public Builder precioOriginal(BigDecimal precioOriginal) {
            instance.precioOriginal = precioOriginal;
            return this;
        }

        public Builder precioPropuesto(BigDecimal precioPropuesto) {
            instance.precioPropuesto = precioPropuesto;
            return this;
        }

        public Builder mensaje(String mensaje) {
            instance.mensaje = mensaje;
            return this;
        }

        public Builder estado(EstadoContraoferta estado) {
            instance.estado = estado;
            return this;
        }

        public Builder fechaEmision(LocalDateTime fechaEmision) {
            instance.fechaEmision = fechaEmision;
            return this;
        }

        public Builder fechaResolucion(LocalDateTime fechaResolucion) {
            instance.fechaResolucion = fechaResolucion;
            return this;
        }

        public ContraofertaResult build() {
            return instance;
        }
    }
}
