package com.servify.autenticacion.application.dto;

import java.util.UUID;

public class CerrarSesionCommand {

    private UUID usuarioId;
    private String refreshToken;

    public CerrarSesionCommand() {
    }

    public CerrarSesionCommand(UUID usuarioId,
                               String refreshToken) {
        this.usuarioId = usuarioId;
        this.refreshToken = refreshToken;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
