package com.servify.solicitudes.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.shared.domain.model.BaseEntity;

//La dejé minimalista porque en el MVP no hay reseña textual, solo estrellas.
//Más adelante, si agregan comentarios o feedback escrito, se puede extender con algo como:
//String comentario
//Boolean visible
//LocalDateTime fechaEdicion

public class Calificacion extends BaseEntity {

    private UUID solicitudId;
    private UUID asignacionServicioId;
    private UUID solicitanteId;
    private UUID prestadorId;
    private Integer puntaje;
    private LocalDateTime fechaCalificacion;

    protected Calificacion() {
    }

    public Calificacion(UUID id,
                        UUID solicitudId,
                        UUID asignacionServicioId,
                        UUID solicitanteId,
                        UUID prestadorId,
                        Integer puntaje,
                        LocalDateTime fechaCalificacion) {
        super(id);
        this.solicitudId = solicitudId;
        this.asignacionServicioId = asignacionServicioId;
        this.solicitanteId = solicitanteId;
        this.prestadorId = prestadorId;
        this.puntaje = puntaje;
        this.fechaCalificacion = fechaCalificacion;
    }

    public UUID getSolicitudId() {
        return solicitudId;
    }

    public UUID getAsignacionServicioId() {
        return asignacionServicioId;
    }

    public UUID getSolicitanteId() {
        return solicitanteId;
    }

    public UUID getPrestadorId() {
        return prestadorId;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public LocalDateTime getFechaCalificacion() {
        return fechaCalificacion;
    }

    public boolean esPuntajeValido() {
        return this.puntaje != null && this.puntaje >= 1 && this.puntaje <= 5;
    }

    public boolean perteneceASolicitud(UUID solicitudId) {
        if (solicitudId == null) {
            return false;
        }
        return this.solicitudId.equals(solicitudId);
    }

    public boolean perteneceAPrestador(UUID prestadorId) {
        if (prestadorId == null) {
            return false;
        }
        return this.prestadorId.equals(prestadorId);
    }

    public boolean fueEmitidaPor(UUID solicitanteId) {
        if (solicitanteId == null) {
            return false;
        }
        return this.solicitanteId.equals(solicitanteId);
    }

    public void actualizarPuntaje(Integer puntaje) {
        if (puntaje == null || puntaje < 1 || puntaje > 5) {
            throw new IllegalArgumentException("El puntaje debe ser un valor entre 1 y 5");
        }
        this.puntaje = puntaje;
    }
}