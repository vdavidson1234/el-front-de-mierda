package com.servify.administracion.application.port.out;

import com.servify.administracion.domain.model.ConfiguracionGeneral;

import java.util.Optional;
import java.util.UUID;

public interface ConfiguracionGeneralRepositoryPort {

    ConfiguracionGeneral guardar(ConfiguracionGeneral configuracionGeneral);

    Optional<ConfiguracionGeneral> buscarPorId(UUID configuracionGeneralId);

    Optional<ConfiguracionGeneral> obtenerVigente();
}
