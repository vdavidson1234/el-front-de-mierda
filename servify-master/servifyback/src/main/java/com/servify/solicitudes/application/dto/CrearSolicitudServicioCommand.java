package com.servify.solicitudes.application.dto;

import com.servify.shared.domain.enumtype.ModalidadServicio;
import com.servify.shared.domain.valueobject.DisponibilidadHoraria;
import com.servify.shared.domain.valueobject.Ubicacion;

import java.math.BigDecimal;
import java.util.UUID;

public class CrearSolicitudServicioCommand {

    private UUID solicitanteId;
    private UUID categoriaServicioId;
    private ModalidadServicio modalidadServicio;
    private Ubicacion ubicacion;
    private DisponibilidadHoraria disponibilidadRequerida;
    private String descripcionNecesidad;
    private BigDecimal precioReferencia;

    public CrearSolicitudServicioCommand() {
    }

    public CrearSolicitudServicioCommand(UUID solicitanteId,
                                         UUID categoriaServicioId,
                                         ModalidadServicio modalidadServicio,
                                         Ubicacion ubicacion,
                                         DisponibilidadHoraria disponibilidadRequerida,
                                         String descripcionNecesidad,
                                         BigDecimal precioReferencia) {
        this.solicitanteId = solicitanteId;
        this.categoriaServicioId = categoriaServicioId;
        this.modalidadServicio = modalidadServicio;
        this.ubicacion = ubicacion;
        this.disponibilidadRequerida = disponibilidadRequerida;
        this.descripcionNecesidad = descripcionNecesidad;
        this.precioReferencia = precioReferencia;
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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public DisponibilidadHoraria getDisponibilidadRequerida() {
        return disponibilidadRequerida;
    }

    public String getDescripcionNecesidad() {
        return descripcionNecesidad;
    }

    public BigDecimal getPrecioReferencia() {
        return precioReferencia;
    }
}