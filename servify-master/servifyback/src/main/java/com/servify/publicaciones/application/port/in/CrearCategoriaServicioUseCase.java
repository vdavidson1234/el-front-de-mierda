package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.CategoriaServicioResult;
import com.servify.publicaciones.application.dto.CrearCategoriaServicioCommand;

/**
 * Puerto de entrada para crear una categoria de servicio.
 */
public interface CrearCategoriaServicioUseCase {

    CategoriaServicioResult crear(CrearCategoriaServicioCommand command);
}
