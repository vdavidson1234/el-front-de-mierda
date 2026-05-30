package com.servify.solicitudes.application.dto;

import java.util.UUID;

public class CancelarSolicitudServicioCommand {

    private UUID solicitudId;
    private UUID solicitanteId;

    public CancelarSolicitudServicioCommand() {
    }

    public CancelarSolicitudServicioCommand(UUID solicitudId, UUID solicitanteId) {
        this.solicitudId = solicitudId;
        this.solicitanteId = solicitanteId;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }
}