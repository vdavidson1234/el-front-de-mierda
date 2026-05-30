package com.servify.autenticacion.application.dto;

import java.util.UUID;

public class RegistrarCredencialesCommand {

    private UUID usuarioId;
    private String emailAcceso;
    private String passwordPlano;

    public RegistrarCredencialesCommand() {
    }

    public RegistrarCredencialesCommand(UUID usuarioId,
                                        String emailAcceso,
                                        String passwordPlano) {
        this.usuarioId = usuarioId;
        this.emailAcceso = emailAcceso;
        this.passwordPlano = passwordPlano;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEmailAcceso() {
        return emailAcceso;
    }

    public String getPasswordPlano() {
        return passwordPlano;
    }
}
