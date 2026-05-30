package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.CategoriaServicioResult;

import java.util.List;

/**
 * Puerto de entrada para listar categorias de servicio activas.
 */
public interface ListarCategoriasActivasUseCase {

    List<CategoriaServicioResult> listarActivas();
}
