package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.CambiarEstadoCategoriaServicioCommand;
import com.servify.publicaciones.application.dto.CategoriaServicioResult;

/**
 * Puerto de entrada para cambiar el estado de una categoria de servicio.
 */
public interface CambiarEstadoCategoriaServicioUseCase {

    CategoriaServicioResult cambiarEstado(CambiarEstadoCategoriaServicioCommand command);
}
