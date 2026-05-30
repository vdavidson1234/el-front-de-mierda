package com.servify.solicitudes.application.dto;

import java.util.UUID;

public class ConfirmarAsignacionSolicitudCommand {

    private UUID solicitudId;
    private UUID distribucionSolicitudId;
    private UUID solicitanteId;

    public ConfirmarAsignacionSolicitudCommand() {
    }

    public ConfirmarAsignacionSolicitudCommand(UUID solicitudId,
                                               UUID distribucionSolicitudId,
                                               UUID solicitanteId) {
        this.solicitudId = solicitudId;
        this.distribucionSolicitudId = distribucionSolicitudId;
        this.solicitanteId = solicitanteId;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getDistribucionSolicitudId() {
        return distribucionSolicitudId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }
}
