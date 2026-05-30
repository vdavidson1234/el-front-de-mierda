package com.servify.autenticacion.application.port.in;

import com.servify.autenticacion.application.dto.RenovarTokenCommand;
import com.servify.autenticacion.application.dto.SesionResult;

public interface RenovarTokenUseCase {

    SesionResult renovar(RenovarTokenCommand command);
}
