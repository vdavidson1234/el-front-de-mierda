package com.servify.administracion.application.port.in;

import com.servify.administracion.application.dto.MedidaAdministrativaUsuarioResult;

import java.util.List;
import java.util.UUID;

public interface ObtenerMedidasAdministrativasDeUsuarioUseCase {

    List<MedidaAdministrativaUsuarioResult> obtenerPorUsuarioId(UUID usuarioId);
}
