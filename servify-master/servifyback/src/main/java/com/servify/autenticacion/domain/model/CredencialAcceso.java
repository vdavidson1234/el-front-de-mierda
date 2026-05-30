package com.servify.autenticacion.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.shared.domain.model.BaseEntity;

public class CredencialAcceso extends BaseEntity {

    private UUID usuarioId;
    private String emailAcceso;
    private String passwordHash;
    private Boolean habilitada;
    private LocalDateTime ultimoAcceso;
    private Integer intentosFallidos;

    protected CredencialAcceso() {
    }

    public CredencialAcceso(UUID id,
                            UUID usuarioId,
                            String emailAcceso,
                            String passwordHash,
                            Boolean habilitada,
                            LocalDateTime ultimoAcceso,
                            Integer intentosFallidos) {
        super(id);
        this.usuarioId = usuarioId;
        this.emailAcceso = emailAcceso;
        this.passwordHash = passwordHash;
        this.habilitada = habilitada;
        this.ultimoAcceso = ultimoAcceso;
        this.intentosFallidos = intentosFallidos;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEmailAcceso() {
        return emailAcceso;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public Integer getIntentosFallidos() {
        return intentosFallidos;
    }

    public boolean estaHabilitada() {
        return this.habilitada != null && this.habilitada;
    }

    public boolean perteneceAUsuario(UUID usuarioId) {
        if (usuarioId == null) {
            return false;
        }
        return this.usuarioId.equals(usuarioId);
    }

    public boolean usaEmail(String emailAcceso) {
        if (emailAcceso == null || this.emailAcceso == null) {
            return false;
        }
        return this.emailAcceso.equalsIgnoreCase(emailAcceso);
    }

    public void actualizarEmailAcceso(String emailAcceso) {
        if (emailAcceso == null || emailAcceso.trim().isEmpty()) {
            throw new IllegalArgumentException("El email de acceso no puede ser nulo o vacío");
        }
        if (!emailAcceso.contains("@")) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }
        this.emailAcceso = emailAcceso;
    }

    public void actualizarPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("El hash de contraseña no puede ser nulo o vacío");
        }
        this.passwordHash = passwordHash;
    }

    public void registrarAccesoExitoso(LocalDateTime fechaAcceso) {
        if (fechaAcceso == null) {
            throw new IllegalArgumentException("La fecha de acceso no puede ser nula");
        }
        this.ultimoAcceso = fechaAcceso;
        this.intentosFallidos = 0;
    }

    public void registrarIntentoFallido() {
        if (this.intentosFallidos == null) {
            this.intentosFallidos = 0;
        }
        this.intentosFallidos++;
    }

    public void reiniciarIntentosFallidos() {
        this.intentosFallidos = 0;
    }

    public void habilitar() {
        this.habilitada = true;
    }

    public void deshabilitar() {
        this.habilitada = false;
    }
}