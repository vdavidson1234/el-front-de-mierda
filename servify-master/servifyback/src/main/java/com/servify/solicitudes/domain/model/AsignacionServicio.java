package com.servify.solicitudes.domain.model;

import com.servify.shared.domain.model.BaseEntity;
import com.servify.solicitudes.domain.enumtype.EstadoAsignacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//Decisiones tomadas:
//solicitudId, distribucionSolicitudId, prestadorId y publicacionServicioId van por ID para no acoplar módulos.
//precioAcordado permite reflejar tanto el precio original como uno surgido de una contraoferta aceptada.
//fechaAsignacion y fechaFinalizacion ayudan con trazabilidad y auditoría básica.

public class AsignacionServicio extends BaseEntity {

    private UUID solicitudId;
    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private UUID publicacionServicioId;
    private BigDecimal precioAcordado;
    private EstadoAsignacion estado;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinalizacion;

    protected AsignacionServicio() {
    }

    public AsignacionServicio(UUID id,
                              UUID solicitudId,
                              UUID distribucionSolicitudId,
                              UUID prestadorId,
                              UUID publicacionServicioId,
                              BigDecimal precioAcordado,
                              EstadoAsignacion estado,
                              LocalDateTime fechaAsignacion,
                              LocalDateTime fechaFinalizacion) {
        super(id);
        this.solicitudId = solicitudId;
        this.distribucionSolicitudId = distribucionSolicitudId;
        this.prestadorId = prestadorId;
        this.publicacionServicioId = publicacionServicioId;
        this.precioAcordado = precioAcordado;
        this.estado = estado;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFinalizacion = fechaFinalizacion;
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

    public boolean estaPendienteConfirmacion() {
        return this.estado == EstadoAsignacion.PENDIENTE_CONFIRMACION;
    }

    public boolean estaActiva() {
        return this.estado == EstadoAsignacion.ACTIVA;
    }

    public boolean estaFinalizada() {
        return this.estado == EstadoAsignacion.FINALIZADA;
    }

    public boolean estaCancelada() {
        return this.estado == EstadoAsignacion.CANCELADA;
    }

    public boolean perteneceAPrestador(UUID prestadorId) {
        return prestadorId != null && Objects.equals(this.prestadorId, prestadorId);
    }

    public boolean correspondeASolicitud(UUID solicitudId) {
        return solicitudId != null && Objects.equals(this.solicitudId, solicitudId);
    }

    public boolean puedeFinalizarse() {
        return estaActiva();
    }

    public void actualizarPrecioAcordado(BigDecimal precioAcordado) {
        if (precioAcordado == null) {
            throw new IllegalArgumentException("El precio acordado no puede ser nulo");
        }
        if (precioAcordado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio acordado no puede ser negativo");
        }
        if (!estaPendienteConfirmacion() && !estaActiva()) {
            throw new IllegalStateException("El precio acordado no puede modificarse en el estado actual");
        }
        this.precioAcordado = precioAcordado;
    }

    public void marcarComoPendienteConfirmacion() {
        if (estaFinalizada() || estaCancelada() || estaActiva()) {
            throw new IllegalStateException("La asignación no puede volver a pendiente de confirmacion");
        }
        this.estado = EstadoAsignacion.PENDIENTE_CONFIRMACION;
        if (this.fechaAsignacion == null) {
            this.fechaAsignacion = LocalDateTime.now();
        }
        this.fechaFinalizacion = null;
    }

    public void activar() {
        if (!estaPendienteConfirmacion()) {
            throw new IllegalStateException("La asignación solo puede activarse desde pendiente de confirmacion");
        }
        this.estado = EstadoAsignacion.ACTIVA;
    }

    public void finalizar(LocalDateTime fechaFinalizacion) {
        if (fechaFinalizacion == null) {
            throw new IllegalArgumentException("La fecha de finalizacion no puede ser nula");
        }
        if (!puedeFinalizarse()) {
            throw new IllegalStateException("La asignacion no puede finalizarse en su estado actual");
        }
        if (this.fechaAsignacion != null && fechaFinalizacion.isBefore(this.fechaAsignacion)) {
            throw new IllegalArgumentException("La fecha de finalizacion debe ser posterior o igual a la fecha de asignacion");
        }
        this.estado = EstadoAsignacion.FINALIZADA;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void cancelar() {
        if (estaFinalizada() || estaCancelada()) {
            throw new IllegalStateException("La asignacion no puede cancelarse en su estado actual");
        }
        this.estado = EstadoAsignacion.CANCELADA;
        this.fechaFinalizacion = LocalDateTime.now();
    }
}