package com.servify.administracion.application.dto;

import com.servify.administracion.domain.enumtype.TipoMedida;

import java.time.LocalDateTime;
import java.util.UUID;

public class AplicarMedidaAdministrativaUsuarioCommand {

    private UUID usuarioId;
    private UUID administradorId;
    private TipoMedida tipoMedida;
    private String motivo;
    private LocalDateTime fechaFinVigencia;

    public AplicarMedidaAdministrativaUsuarioCommand() {
    }

    public AplicarMedidaAdministrativaUsuarioCommand(UUID usuarioId,
                                                    UUID administradorId,
                                                    TipoMedida tipoMedida,
                                                    String motivo,
                                                    LocalDateTime fechaFinVigencia) {
        this.usuarioId = usuarioId;
        this.administradorId = administradorId;
        this.tipoMedida = tipoMedida;
        this.motivo = motivo;
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public UUID getAdministradorId() {
        return administradorId;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFechaFinVigencia() {
        return fechaFinVigencia;
    }
}
