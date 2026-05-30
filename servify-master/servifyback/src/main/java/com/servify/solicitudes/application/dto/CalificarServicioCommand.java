package com.servify.solicitudes.application.dto;

import java.util.UUID;

/**
 * Es el DTO de entrada del caso de uso mediante el cual el solicitante
   califica al prestador una vez finalizado el servicio.

 * solicitudId: identifica la prestación cerrada
 * asignacionServicioId: asegura vínculo con la asignación efectiva
 * solicitanteId: valida que califica quien recibió el servicio
 * prestadorId: deja explícito a quién se califica
 * puntaje: score simple de 1 a 5
 */

public class CalificarServicioCommand {

    private UUID solicitudId;
    private UUID asignacionServicioId;
    private UUID solicitanteId;
    private UUID prestadorId;
    private Integer puntaje;

    public CalificarServicioCommand() {
    }

    public CalificarServicioCommand(UUID solicitudId,
                                    UUID asignacionServicioId,
                                    UUID solicitanteId,
                                    UUID prestadorId,
                                    Integer puntaje) {
        this.solicitudId = solicitudId;
        this.asignacionServicioId = asignacionServicioId;
        this.solicitanteId = solicitanteId;
        this.prestadorId = prestadorId;
        this.puntaje = puntaje;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getAsignacionServicioId() {
        return asignacionServicioId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public Integer getPuntaje() {
        return puntaje;
    }
}