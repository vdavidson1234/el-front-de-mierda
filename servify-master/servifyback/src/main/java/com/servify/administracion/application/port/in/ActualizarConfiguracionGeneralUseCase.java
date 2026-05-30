package com.servify.administracion.application.port.in;

import com.servify.administracion.application.dto.ActualizarConfiguracionGeneralCommand;
import com.servify.administracion.application.dto.ConfiguracionGeneralResult;

public interface ActualizarConfiguracionGeneralUseCase {

    ConfiguracionGeneralResult actualizar(ActualizarConfiguracionGeneralCommand command);
}
