package com.servify.administracion.application.dto;

import com.servify.administracion.domain.enumtype.TipoMedida;

import java.time.LocalDateTime;
import java.util.UUID;

public class MedidaAdministrativaUsuarioResult {

    private UUID id;
    private UUID usuarioId;
    private UUID administradorId;
    private TipoMedida tipoMedida;
    private String motivo;
    private LocalDateTime fechaAplicacion;
    private LocalDateTime fechaFinVigencia;
    private Boolean activa;

    private MedidaAdministrativaUsuarioResult() {
    }

    public UUID getId() {
        return id;
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

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public LocalDateTime getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public Boolean getActiva() {
        return activa;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final MedidaAdministrativaUsuarioResult instance;

        public Builder() {
            this.instance = new MedidaAdministrativaUsuarioResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder usuarioId(UUID usuarioId) {
            instance.usuarioId = usuarioId;
            return this;
        }

        public Builder administradorId(UUID administradorId) {
            instance.administradorId = administradorId;
            return this;
        }

        public Builder tipoMedida(TipoMedida tipoMedida) {
            instance.tipoMedida = tipoMedida;
            return this;
        }

        public Builder motivo(String motivo) {
            instance.motivo = motivo;
            return this;
        }

        public Builder fechaAplicacion(LocalDateTime fechaAplicacion) {
            instance.fechaAplicacion = fechaAplicacion;
            return this;
        }

        public Builder fechaFinVigencia(LocalDateTime fechaFinVigencia) {
            instance.fechaFinVigencia = fechaFinVigencia;
            return this;
        }

        public Builder activa(Boolean activa) {
            instance.activa = activa;
            return this;
        }

        public MedidaAdministrativaUsuarioResult build() {
            return instance;
        }
    }
}
