package com.servify.solicitudes.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class EmitirContraofertaCommand {

    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private BigDecimal precioPropuesto;
    private String mensaje;

    public EmitirContraofertaCommand() {
    }

    public EmitirContraofertaCommand(UUID distribucionSolicitudId,
                                     UUID prestadorId,
                                     BigDecimal precioPropuesto,
                                     String mensaje) {
        this.distribucionSolicitudId = distribucionSolicitudId;
        this.prestadorId = prestadorId;
        this.precioPropuesto = precioPropuesto;
        this.mensaje = mensaje;
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public BigDecimal getPrecioPropuesto() {
        return precioPropuesto;
    }

    public String getMensaje() {
        return mensaje;
    }
}