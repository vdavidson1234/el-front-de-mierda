package com.servify.usuarios.application.dto;

import com.servify.usuarios.domain.enumtype.EstadoUsuario;

import java.util.UUID;

public class CambiarEstadoUsuarioCommand {

    private UUID usuarioId;
    private EstadoUsuario nuevoEstado;

    public CambiarEstadoUsuarioCommand() {
    }

    public CambiarEstadoUsuarioCommand(UUID usuarioId, EstadoUsuario nuevoEstado) {
        this.usuarioId = usuarioId;
        this.nuevoEstado = nuevoEstado;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public EstadoUsuario getNuevoEstado() {
        return nuevoEstado;
    }
}