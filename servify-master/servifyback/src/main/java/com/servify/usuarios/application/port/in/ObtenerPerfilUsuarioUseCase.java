package com.servify.usuarios.application.port.in;

import com.servify.usuarios.application.dto.PerfilUsuarioResult;

import java.util.Optional;
import java.util.UUID;

public interface ObtenerPerfilUsuarioUseCase {

    Optional<PerfilUsuarioResult> obtenerPorUsuarioId(UUID usuarioId);

    Optional<PerfilUsuarioResult> obtenerPorPerfilId(UUID perfilId);
}