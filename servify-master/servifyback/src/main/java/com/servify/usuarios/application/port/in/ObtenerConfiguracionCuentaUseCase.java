package com.servify.usuarios.application.port.in;

import com.servify.usuarios.application.dto.ConfiguracionCuentaResult;

import java.util.Optional;
import java.util.UUID;

public interface ObtenerConfiguracionCuentaUseCase {

    Optional<ConfiguracionCuentaResult> obtenerPorUsuarioId(UUID usuarioId);
}