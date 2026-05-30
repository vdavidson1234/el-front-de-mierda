package com.servify.administracion.application.port.in;

import com.servify.administracion.application.dto.ConfiguracionGeneralResult;

import java.util.Optional;
import java.util.UUID;

public interface ObtenerConfiguracionGeneralUseCase {

    Optional<ConfiguracionGeneralResult> obtenerVigente();

    Optional<ConfiguracionGeneralResult> obtenerPorId(UUID configuracionGeneralId);
}
