package com.servify.publicaciones.application.dto;

import com.servify.publicaciones.domain.enumtype.EstadoCategoria;

import java.util.UUID;

/**
 * DTO de comando para cambiar el estado de una categoria de servicio.
 */
public class CambiarEstadoCategoriaServicioCommand {

    private UUID categoriaServicioId;
    private EstadoCategoria estadoDestino;
    private String motivo;

    public CambiarEstadoCategoriaServicioCommand() {
    }

    public CambiarEstadoCategoriaServicioCommand(UUID categoriaServicioId,
                                                 EstadoCategoria estadoDestino,
                                                 String motivo) {
        this.categoriaServicioId = categoriaServicioId;
        this.estadoDestino = estadoDestino;
        this.motivo = motivo;
    }

    public UUID getCategoriaServicioId() {
        return categoriaServicioId;
    }

    public EstadoCategoria getEstadoDestino() {
        return estadoDestino;
    }

    public String getMotivo() {
        return motivo;
    }
}
