package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.CambiarEstadoPublicacionCommand;
import com.servify.publicaciones.application.dto.PublicacionServicioResult;

/**
 * Puerto de entrada para cambiar el estado de una publicacion de servicio.
 */
public interface CambiarEstadoPublicacionUseCase {

    PublicacionServicioResult cambiarEstado(CambiarEstadoPublicacionCommand command);
}
