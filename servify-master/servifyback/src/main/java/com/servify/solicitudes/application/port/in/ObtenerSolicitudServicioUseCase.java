package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.SolicitudServicioResult;

import java.util.Optional;
import java.util.UUID;


/**
 Es el puerto de entrada del caso de uso para consultar una solicitud concreta por su id.
 Lo dejé con Optional porque una consulta puede no encontrar la solicitud,
 y así no forzamos todavía una excepción de aplicación.
 */

public interface ObtenerSolicitudServicioUseCase {

    Optional<SolicitudServicioResult> obtenerPorId(UUID solicitudId);
}