package com.servify.solicitudes.application.dto;

import java.util.UUID;

/**
 * DTO de comando para responder a una distribucion de solicitud.

 distribucionSolicitudId: identifica la distribución puntual que recibió el prestador
 prestadorId: valida que responde quien corresponde
 tipoRespuesta: evita separar en dos casos de uso mínimos para aceptar y rechazar
 */

public class ResponderDistribucionSolicitudCommand {

    private UUID distribucionSolicitudId;
    private UUID prestadorId;
    private TipoRespuestaDistribucion tipoRespuesta;

    public ResponderDistribucionSolicitudCommand() {
    }

    public ResponderDistribucionSolicitudCommand(UUID distribucionSolicitudId,
                                                 UUID prestadorId,
                                                 TipoRespuestaDistribucion tipoRespuesta) {
        this.distribucionSolicitudId = distribucionSolicitudId;
        this.prestadorId = prestadorId;
        this.tipoRespuesta = tipoRespuesta;
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public TipoRespuestaDistribucion getTipoRespuesta() {
        return tipoRespuesta;
    }
}