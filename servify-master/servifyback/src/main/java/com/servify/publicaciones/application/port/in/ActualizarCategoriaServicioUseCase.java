package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.ActualizarCategoriaServicioCommand;
import com.servify.publicaciones.application.dto.CategoriaServicioResult;

/**
 * Puerto de entrada para actualizar una categoria de servicio.
 */
public interface ActualizarCategoriaServicioUseCase {

    CategoriaServicioResult actualizar(ActualizarCategoriaServicioCommand command);
}
