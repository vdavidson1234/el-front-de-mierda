package com.servify.autenticacion.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.shared.domain.model.BaseEntity;

public class RefreshToken extends BaseEntity {

    private UUID usuarioId;
    private UUID credencialAccesoId;
    private String tokenHash;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaExpiracion;
    private LocalDateTime fechaRevocacion;
    private Boolean activo;

    protected RefreshToken() {
    }

    public RefreshToken(UUID id,
                        UUID usuarioId,
                        UUID credencialAccesoId,
                        String tokenHash,
                        LocalDateTime fechaEmision,
                        LocalDateTime fechaExpiracion,
                        LocalDateTime fechaRevocacion,
                        Boolean activo) {
        super(id);
        this.usuarioId = usuarioId;
        this.credencialAccesoId = credencialAccesoId;
        this.tokenHash = tokenHash;
        this.fechaEmision = fechaEmision;
        this.fechaExpiracion = fechaExpiracion;
        this.fechaRevocacion = fechaRevocacion;
        this.activo = activo;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public UUID getCredencialAccesoId() {
        return credencialAccesoId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public LocalDateTime getFechaRevocacion() {
        return fechaRevocacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public boolean estaActivo() {
        return this.activo != null && this.activo && !fueRevocado();
    }

    public boolean estaExpirado(LocalDateTime fechaActual) {
        if (this.fechaExpiracion == null || fechaActual == null) {
            return false;
        }
        return fechaActual.isAfter(this.fechaExpiracion);
    }

    public boolean fueRevocado() {
        return this.fechaRevocacion != null || (this.activo != null && !this.activo);
    }

    public boolean perteneceAUsuario(UUID usuarioId) {
        if (usuarioId == null) {
            return false;
        }
        return this.usuarioId.equals(usuarioId);
    }

    public void revocar(LocalDateTime fechaRevocacion) {
        if (fechaRevocacion == null) {
            throw new IllegalArgumentException("La fecha de revocación no puede ser nula");
        }
        this.activo = false;
        this.fechaRevocacion = fechaRevocacion;
    }
}
