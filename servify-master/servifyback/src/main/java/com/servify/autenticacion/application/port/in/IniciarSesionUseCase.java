package com.servify.autenticacion.application.port.in;

import com.servify.autenticacion.application.dto.IniciarSesionCommand;
import com.servify.autenticacion.application.dto.SesionResult;

public interface IniciarSesionUseCase {

    SesionResult iniciar(IniciarSesionCommand command);
}
