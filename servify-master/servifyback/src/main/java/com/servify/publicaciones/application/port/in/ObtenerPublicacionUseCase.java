package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.PublicacionServicioResult;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de entrada para obtener una publicacion por identificador.
 */
public interface ObtenerPublicacionUseCase {

    Optional<PublicacionServicioResult> obtenerPorId(UUID publicacionServicioId);
}
