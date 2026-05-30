package com.servify.publicaciones.application.port.in;

import com.servify.publicaciones.application.dto.PublicacionServicioResult;

import java.util.List;
import java.util.UUID;

/**
 * Puerto de entrada para listar publicaciones de un usuario.
 */
public interface ListarPublicacionesDeUsuarioUseCase {

    List<PublicacionServicioResult> listarPorUsuarioId(UUID usuarioId);
}
