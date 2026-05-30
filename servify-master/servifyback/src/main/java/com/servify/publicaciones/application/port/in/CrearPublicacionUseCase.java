package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.CrearPublicacionCommand;
import com.servify.publicaciones.application.dto.PublicacionServicioResult;

/**
 * Puerto de entrada para crear una publicacion de servicio.
 */
public interface CrearPublicacionUseCase {

    PublicacionServicioResult crear(CrearPublicacionCommand command);
}
