package com.servify.autenticacion.application.dto;

public class RenovarTokenCommand {

    private String refreshToken;

    public RenovarTokenCommand() {
    }

    public RenovarTokenCommand(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
