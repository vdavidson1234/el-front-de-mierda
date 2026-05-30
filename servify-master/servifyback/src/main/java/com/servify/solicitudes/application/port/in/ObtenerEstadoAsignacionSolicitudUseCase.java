package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.EstadoAsignacionSolicitudResult;

import java.util.UUID;

public interface ObtenerEstadoAsignacionSolicitudUseCase {

    EstadoAsignacionSolicitudResult obtenerEstado(UUID solicitudId);
}
