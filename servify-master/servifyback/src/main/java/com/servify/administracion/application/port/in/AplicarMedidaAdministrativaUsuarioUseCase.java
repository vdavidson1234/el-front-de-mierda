package com.servify.administracion.application.port.in;

import com.servify.administracion.application.dto.AplicarMedidaAdministrativaUsuarioCommand;
import com.servify.administracion.application.dto.MedidaAdministrativaUsuarioResult;

public interface AplicarMedidaAdministrativaUsuarioUseCase {

    MedidaAdministrativaUsuarioResult aplicar(AplicarMedidaAdministrativaUsuarioCommand command);
}
