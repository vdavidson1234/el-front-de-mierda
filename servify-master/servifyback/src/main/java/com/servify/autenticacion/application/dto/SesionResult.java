package com.servify.autenticacion.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class SesionResult {

    private UUID usuarioId;
    private String emailAcceso;
    private TokenResult accessToken;
    private TokenResult refreshToken;
    private LocalDateTime fechaInicioSesion;

    private SesionResult() {
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEmailAcceso() {
        return emailAcceso;
    }

    public TokenResult getAccessToken() {
        return accessToken;
    }

    public TokenResult getRefreshToken() {
        return refreshToken;
    }

    public LocalDateTime getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final SesionResult instance;

        public Builder() {
            this.instance = new SesionResult();
        }

        public Builder usuarioId(UUID usuarioId) {
            instance.usuarioId = usuarioId;
            return this;
        }

        public Builder emailAcceso(String emailAcceso) {
            instance.emailAcceso = emailAcceso;
            return this;
        }

        public Builder accessToken(TokenResult accessToken) {
            instance.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(TokenResult refreshToken) {
            instance.refreshToken = refreshToken;
            return this;
        }

        public Builder fechaInicioSesion(LocalDateTime fechaInicioSesion) {
            instance.fechaInicioSesion = fechaInicioSesion;
            return this;
        }

        public SesionResult build() {
            return instance;
        }
    }
}
