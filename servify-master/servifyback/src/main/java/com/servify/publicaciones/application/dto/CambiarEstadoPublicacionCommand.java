package com.servify.publicaciones.application.dto;

import com.servify.publicaciones.domain.enumtype.EstadoPublicacion;

import java.util.UUID;

/**
 * DTO de comando para cambiar el estado de una publicacion de servicio.
 */
public class CambiarEstadoPublicacionCommand {

    private UUID publicacionServicioId;
    private UUID usuarioId;
    private EstadoPublicacion estadoDestino;
    private String motivo;

    public CambiarEstadoPublicacionCommand() {
    }

    public CambiarEstadoPublicacionCommand(UUID publicacionServicioId,
                                           UUID usuarioId,
                                           EstadoPublicacion estadoDestino,
                                           String motivo) {
        this.publicacionServicioId = publicacionServicioId;
        this.usuarioId = usuarioId;
        this.estadoDestino = estadoDestino;
        this.motivo = motivo;
    }

    public UUID getPublicacionServicioId() {
        return publicacionServicioId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public EstadoPublicacion getEstadoDestino() {
        return estadoDestino;
    }

    public String getMotivo() {
        return motivo;
    }
}
