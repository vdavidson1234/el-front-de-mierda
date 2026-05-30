package com.servify.solicitudes.domain.model;

import com.servify.shared.domain.model.BaseEntity;
import com.servify.solicitudes.domain.enumtype.EstadoContraoferta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//Metí estos atributos porque suelen ser los justos y necesarios:
//distribucionSolicitudId: ancla la contraoferta a una distribución concreta
//prestadorId: deja claro quién la emitió
//precioOriginal y precioPropuesto: permiten trazabilidad
//mensaje: para aclaración breve del prestador
//estado, fechaEmision, fechaResolucion: para ciclo de vida

public class Contraoferta extends BaseEntity {

    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private BigDecimal precioOriginal;
    private BigDecimal precioPropuesto;
    private String mensaje;
    private EstadoContraoferta estado;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaResolucion;

    protected Contraoferta() {
    }

    public Contraoferta(UUID id,
                        UUID distribucionSolicitudId,
                        UUID prestadorId,
                        BigDecimal precioOriginal,
                        BigDecimal precioPropuesto,
                        String mensaje,
                        EstadoContraoferta estado,
                        LocalDateTime fechaEmision,
                        LocalDateTime fechaResolucion) {
        super(id);
        this.distribucionSolicitudId = distribucionSolicitudId;
        this.prestadorId = prestadorId;
        this.precioOriginal = precioOriginal;
        this.precioPropuesto = precioPropuesto;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.fechaResolucion = fechaResolucion;
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

    public boolean estaPendiente() {
        return this.estado == EstadoContraoferta.PENDIENTE;
    }

    public boolean estaAceptada() {
        return this.estado == EstadoContraoferta.ACEPTADA;
    }

    public boolean estaRechazada() {
        return this.estado == EstadoContraoferta.RECHAZADA;
    }

    public boolean estaCancelada() {
        return this.estado == EstadoContraoferta.CANCELADA;
    }

    public boolean puedeSerResuelta() {
        return estaPendiente();
    }

    public boolean perteneceAPrestador(UUID prestadorId) {
        if (prestadorId == null) {
            return false;
        }
        return this.prestadorId != null && this.prestadorId.equals(prestadorId);
    }

    public void actualizarMensaje(String mensaje) {
        if (!estaPendiente()) {
            throw new IllegalStateException("No se puede modificar el mensaje de una contraoferta no pendiente");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo o vacío");
        }
        if (mensaje.length() > 500) {
            throw new IllegalArgumentException("El mensaje no puede exceder 500 caracteres");
        }
        this.mensaje = mensaje;
    }

    public void actualizarPrecioPropuesto(BigDecimal precioPropuesto) {
        if (!estaPendiente()) {
            throw new IllegalStateException("No se puede modificar el precio de una contraoferta no pendiente");
        }
        if (precioPropuesto == null) {
            throw new IllegalArgumentException("El precio propuesto no puede ser nulo");
        }
        if (precioPropuesto.signum() < 0) {
            throw new IllegalArgumentException("El precio propuesto no puede ser negativo");
        }
        this.precioPropuesto = precioPropuesto;
    }

    public void aceptar(LocalDateTime fechaResolucion) {
        if (!puedeSerResuelta()) {
            throw new IllegalStateException("La contraoferta no puede ser resuelta");
        }
        if (fechaResolucion == null) {
            throw new IllegalArgumentException("La fecha de resolución no puede ser nula");
        }
        this.estado = EstadoContraoferta.ACEPTADA;
        this.fechaResolucion = fechaResolucion;
    }

    public void rechazar(LocalDateTime fechaResolucion) {
        if (!puedeSerResuelta()) {
            throw new IllegalStateException("La contraoferta no puede ser resuelta");
        }
        if (fechaResolucion == null) {
            throw new IllegalArgumentException("La fecha de resolución no puede ser nula");
        }
        this.estado = EstadoContraoferta.RECHAZADA;
        this.fechaResolucion = fechaResolucion;
    }

    public void cancelar(LocalDateTime fechaResolucion) {
        if (fechaResolucion == null) {
            throw new IllegalArgumentException("La fecha de resolución no puede ser nula");
        }
        this.estado = EstadoContraoferta.CANCELADA;
        this.fechaResolucion = fechaResolucion;
    }
}