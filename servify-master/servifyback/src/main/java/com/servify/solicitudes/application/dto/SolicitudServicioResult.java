package com.servify.solicitudes.application.dto;

import com.servify.shared.domain.enumtype.ModalidadServicio;
import com.servify.solicitudes.domain.enumtype.EstadoSolicitud;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class SolicitudServicioResult {

    private UUID id;
    private UUID solicitanteId;
    private UUID categoriaServicioId;
    private ModalidadServicio modalidadServicio;
    private UbicacionSolicitudResult ubicacion;
    private DisponibilidadHorariaResult disponibilidadRequerida;
    private String descripcionNecesidad;
    private BigDecimal precioReferencia;
    private EstadoSolicitud estado;
    private LocalDateTime fechaSolicitud;

    public SolicitudServicioResult() {
    }

    public SolicitudServicioResult(UUID id,
                                   UUID solicitanteId,
                                   UUID categoriaServicioId,
                                   ModalidadServicio modalidadServicio,
                                   UbicacionSolicitudResult ubicacion,
                                   DisponibilidadHorariaResult disponibilidadRequerida,
                                   String descripcionNecesidad,
                                   BigDecimal precioReferencia,
                                   EstadoSolicitud estado,
                                   LocalDateTime fechaSolicitud) {
        this.id = id;
        this.solicitanteId = solicitanteId;
        this.categoriaServicioId = categoriaServicioId;
        this.modalidadServicio = modalidadServicio;
        this.ubicacion = ubicacion;
        this.disponibilidadRequerida = disponibilidadRequerida;
        this.descripcionNecesidad = descripcionNecesidad;
        this.precioReferencia = precioReferencia;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public UUID getId() {
        return id;
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

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
}