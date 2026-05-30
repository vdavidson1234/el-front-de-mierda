package com.servify.solicitudes.application.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import com.servify.solicitudes.application.dto.CalificarServicioCommand;
import com.servify.solicitudes.application.port.in.CalificarServicioUseCase;
import com.servify.solicitudes.application.port.out.AsignacionServicioRepositoryPort;
import com.servify.solicitudes.application.port.out.CalificacionRepositoryPort;
import com.servify.solicitudes.application.port.out.SolicitudServicioRepositoryPort;
import com.servify.solicitudes.domain.model.AsignacionServicio;
import com.servify.solicitudes.domain.model.Calificacion;
import com.servify.solicitudes.domain.model.SolicitudServicio;
import com.servify.solicitudes.domain.service.PoliticaCalificacion;


/**
 * Es la implementación del caso de uso mediante el cual el solicitante califica al prestador una vez finalizado el servicio.
 * El MVP exige que:
 * solo se califique una solicitud finalizada
 * la calificación sea de 1 a 5 estrellas
 * y que no exista más de una calificación por solicitud
 */

public class CalificarServicioService implements CalificarServicioUseCase {

    private final CalificacionRepositoryPort calificacionRepositoryPort;
    private final SolicitudServicioRepositoryPort solicitudServicioRepositoryPort;
    private final AsignacionServicioRepositoryPort asignacionServicioRepositoryPort;
    private final PoliticaCalificacion politicaCalificacion;

    public CalificarServicioService(CalificacionRepositoryPort calificacionRepositoryPort,
                                    SolicitudServicioRepositoryPort solicitudServicioRepositoryPort,
                                    AsignacionServicioRepositoryPort asignacionServicioRepositoryPort,
                                    PoliticaCalificacion politicaCalificacion) {
        this.calificacionRepositoryPort = calificacionRepositoryPort;
        this.solicitudServicioRepositoryPort = solicitudServicioRepositoryPort;
        this.asignacionServicioRepositoryPort = asignacionServicioRepositoryPort;
        this.politicaCalificacion = politicaCalificacion;
    }

    @Override
    public void calificar(CalificarServicioCommand command) {
        /**
         * Ejecuta la lógica de calificación del servicio.
         * - Valida campos del comando.
         * - Recupera la solicitud y la asignación asociada.
         * - Verifica correspondencia y permisos del solicitante/prestador.
         * - Valida que la calificación esté permitida por la política y luego persiste la calificación.
         */
        if (command == null) {
            throw new IllegalArgumentException("El comando de calificación no puede ser nulo");
        }
        
        if (command.getSolicitudId() == null || command.getAsignacionServicioId() == null ||
                command.getSolicitanteId() == null || command.getPrestadorId() == null ||
                command.getPuntaje() == null) {
            throw new IllegalArgumentException("Todos los campos del comando de calificación son obligatorios");
        }
        
        SolicitudServicio solicitudServicio = obtenerSolicitudExistente(command.getSolicitudId());
        AsignacionServicio asignacionServicio = obtenerAsignacionExistente(command.getAsignacionServicioId());
        
        validarCorrespondenciaSolicitudAsignacion(solicitudServicio, asignacionServicio);
        validarSolicitante(solicitudServicio, command.getSolicitanteId());
        validarPrestadorAsignado(asignacionServicio, command.getPrestadorId());
        
        validarCalificacionPermitida(solicitudServicio, command.getPrestadorId(), command.getPuntaje());
        
        LocalDateTime fechaCalificacion = obtenerFechaActual();
        Calificacion calificacion = construirCalificacion(command, fechaCalificacion);
        
        persistirCalificacion(calificacion);
    }

    protected SolicitudServicio obtenerSolicitudExistente(UUID solicitudId) {
        if (solicitudId == null) {
            throw new IllegalArgumentException("El ID de la solicitud no puede ser nulo");
        }
        
        return solicitudServicioRepositoryPort.buscarPorId(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La solicitud con ID " + solicitudId + " no existe"));
    }

    protected AsignacionServicio obtenerAsignacionExistente(UUID asignacionServicioId) {
        if (asignacionServicioId == null) {
            throw new IllegalArgumentException("El ID de la asignación no puede ser nulo");
        }
        
        return asignacionServicioRepositoryPort.buscarPorId(asignacionServicioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La asignación con ID " + asignacionServicioId + " no existe"));
    }

    protected void validarCorrespondenciaSolicitudAsignacion(SolicitudServicio solicitudServicio,
                                                             AsignacionServicio asignacionServicio) {
        if (!asignacionServicio.getSolicitudId().equals(solicitudServicio.getId())) {
            throw new IllegalArgumentException(
                    "La asignación no corresponde a la solicitud indicada");
        }
    }

    protected void validarSolicitante(SolicitudServicio solicitudServicio, UUID solicitanteId) {
        if (!solicitudServicio.getSolicitanteId().equals(solicitanteId)) {
            throw new IllegalArgumentException(
                    "Solo el solicitante puede calificar el servicio");
        }
    }

    protected void validarPrestadorAsignado(AsignacionServicio asignacionServicio, UUID prestadorId) {
        if (!asignacionServicio.getPrestadorId().equals(prestadorId)) {
            throw new IllegalArgumentException(
                    "El prestador calificado no corresponde al asignado");
        }
    }

    protected void validarCalificacionPermitida(SolicitudServicio solicitudServicio,
                                                UUID prestadorId,
                                                Integer puntaje) {
        /**
         * Valida que la calificación esté permitida según la política del dominio.
         * - Comprueba si ya existe una calificación para la solicitud.
         * - Verifica que la solicitud esté en el estado esperado y que el puntaje sea válido.
         */
        List<Calificacion> calificacionesExistentes = calificacionRepositoryPort.buscarPorSolicitudId(solicitudServicio.getId())
            .map(List::of)
            .orElseGet(List::of);

        if (!politicaCalificacion.puedeCalificarse(solicitudServicio, calificacionesExistentes, prestadorId)) {
            throw new IllegalArgumentException(
                "No se puede calificar este servicio. Verifique que la solicitud esté finalizada " +
                    "y que no exista una calificación previa");
        }

        if (!politicaCalificacion.puntajePermitido(puntaje)) {
            throw new IllegalArgumentException(
                "El puntaje debe ser un valor entre 1 y 5");
        }
    }

    protected Calificacion construirCalificacion(CalificarServicioCommand command,
                                                 LocalDateTime fechaCalificacion) {
        /**
         * Construye la entidad `Calificacion` a persistir con un identificador generado
         * y la fecha de calificación proporcionada.
         */
        UUID calificacionId = generarIdCalificacion();

        return new Calificacion(
            calificacionId,
            command.getSolicitudId(),
            command.getAsignacionServicioId(),
            command.getSolicitanteId(),
            command.getPrestadorId(),
            command.getPuntaje(),
            fechaCalificacion
        );
    }

    protected void persistirCalificacion(Calificacion calificacion) {
        calificacionRepositoryPort.guardar(calificacion);
    }

    protected UUID generarIdCalificacion() {
        return UUID.randomUUID();
    }

    protected LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }
}