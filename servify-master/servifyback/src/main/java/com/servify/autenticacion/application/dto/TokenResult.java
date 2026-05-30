package com.servify.autenticacion.application.dto;

import java.time.LocalDateTime;

public class TokenResult {

    private String token;
    private String tipoToken;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaExpiracion;

    public TokenResult() {
    }

    public TokenResult(String token,
                       String tipoToken,
                       LocalDateTime fechaEmision,
                       LocalDateTime fechaExpiracion) {
        this.token = token;
        this.tipoToken = tipoToken;
        this.fechaEmision = fechaEmision;
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getToken() {
        return token;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
}
