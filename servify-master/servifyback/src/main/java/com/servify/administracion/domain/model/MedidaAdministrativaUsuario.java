package com.servify.administracion.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.administracion.domain.enumtype.TipoMedida;
import com.servify.shared.domain.model.BaseEntity;

public class MedidaAdministrativaUsuario extends BaseEntity {

    private UUID usuarioId;
    private UUID administradorId;
    private TipoMedida tipoMedida;
    private String motivo;
    private LocalDateTime fechaAplicacion;
    private LocalDateTime fechaFinVigencia;
    private Boolean activa;

    protected MedidaAdministrativaUsuario() {
    }

    public MedidaAdministrativaUsuario(UUID id,
                                       UUID usuarioId,
                                       UUID administradorId,
                                       TipoMedida tipoMedida,
                                       String motivo,
                                       LocalDateTime fechaAplicacion,
                                       LocalDateTime fechaFinVigencia,
                                       Boolean activa) {
        super(id);
        this.usuarioId = usuarioId;
        this.administradorId = administradorId;
        this.tipoMedida = tipoMedida;
        this.motivo = motivo;
        this.fechaAplicacion = fechaAplicacion;
        this.fechaFinVigencia = fechaFinVigencia;
        this.activa = activa;
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

    public boolean esBloqueo() {
        return this.tipoMedida == TipoMedida.BLOQUEO;
    }

    public boolean esSuspension() {
        return this.tipoMedida == TipoMedida.SUSPENSION;
    }

    public boolean esAdvertencia() {
        return this.tipoMedida == TipoMedida.ADVERTENCIA;
    }

    public boolean estaActiva() {
        return this.activa != null && this.activa;
    }

    public boolean tieneVigenciaTemporal() {
        return this.fechaFinVigencia != null;
    }

    public boolean estaVencida(LocalDateTime fechaActual) {
        if (!tieneVigenciaTemporal()) {
            return false;
        }
        return fechaActual.isAfter(this.fechaFinVigencia);
    }

    public boolean aplicaAUsuario(UUID usuarioId) {
        if (usuarioId == null) {
            return false;
        }
        return this.usuarioId.equals(usuarioId);
    }

    public boolean fueAplicadaPor(UUID administradorId) {
        if (administradorId == null) {
            return false;
        }
        return this.administradorId.equals(administradorId);
    }

    public void actualizarMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede ser nulo o vacío");
        }
        if (motivo.length() > 500) {
            throw new IllegalArgumentException("El motivo no puede exceder 500 caracteres");
        }
        this.motivo = motivo;
    }

    public void actualizarFechaFinVigencia(LocalDateTime fechaFinVigencia) {
        if (fechaFinVigencia != null && !fechaFinVigencia.isAfter(this.fechaAplicacion)) {
            throw new IllegalArgumentException("La fecha de fin de vigencia debe ser posterior a la fecha de aplicación");
        }
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }
}