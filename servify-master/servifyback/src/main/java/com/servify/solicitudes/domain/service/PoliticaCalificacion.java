package com.servify.solicitudes.domain.service;

import com.servify.solicitudes.domain.model.Calificacion;
import com.servify.solicitudes.domain.model.SolicitudServicio;
import com.servify.solicitudes.domain.enumtype.EstadoSolicitud;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//sta política evita repartir validaciones entre:
//Calificacion
//SolicitudServicio
//casos de uso
//controllers


public class PoliticaCalificacion {

    public boolean puedeCalificarse(SolicitudServicio solicitud,
                                    List<Calificacion> calificacionesExistentes,
                                    UUID prestadorId) {
        return solicitud != null
                && solicitud.getEstado() == EstadoSolicitud.FINALIZADA
                && prestadorId != null
                && !yaFueCalificada(solicitud, calificacionesExistentes);
    }

    public boolean yaFueCalificada(SolicitudServicio solicitud,
                                   List<Calificacion> calificacionesExistentes) {
        if (solicitud == null || calificacionesExistentes == null || calificacionesExistentes.isEmpty()) {
            return false;
        }

        return calificacionesExistentes.stream()
                .anyMatch(calificacion -> calificacion != null
                        && Objects.equals(calificacion.getSolicitudId(), solicitud.getId()));
    }

    public boolean correspondeAlPrestadorAsignado(UUID prestadorId,
                                                  UUID prestadorAsignadoId) {
        return prestadorId != null && Objects.equals(prestadorId, prestadorAsignadoId);
    }

    public boolean puntajePermitido(Integer puntaje) {
        return puntaje != null && puntaje >= 1 && puntaje <= 5;
    }
}
