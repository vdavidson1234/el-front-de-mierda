package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.ActualizarPublicacionCommand;
import com.servify.publicaciones.application.dto.PublicacionServicioResult;

/**
 * Puerto de entrada para actualizar una publicacion de servicio.
 */
public interface ActualizarPublicacionUseCase {

    PublicacionServicioResult actualizar(ActualizarPublicacionCommand command);
}
