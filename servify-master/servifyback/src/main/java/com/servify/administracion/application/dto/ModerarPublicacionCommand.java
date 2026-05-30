package com.servify.administracion.application.dto;

import java.util.UUID;

public class ModerarPublicacionCommand {

    private UUID publicacionServicioId;
    private UUID administradorId;
    private String estadoDestino;
    private String motivo;

    public ModerarPublicacionCommand() {
    }

    public ModerarPublicacionCommand(UUID publicacionServicioId,
                                     UUID administradorId,
                                     String estadoDestino,
                                     String motivo) {
        this.publicacionServicioId = publicacionServicioId;
        this.administradorId = administradorId;
        this.estadoDestino = estadoDestino;
        this.motivo = motivo;
    }

    public UUID getPublicacionServicioId() {
        return publicacionServicioId;
    }

    public UUID getAdministradorId() {
        return administradorId;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public String getMotivo() {
        return motivo;
    }
}
