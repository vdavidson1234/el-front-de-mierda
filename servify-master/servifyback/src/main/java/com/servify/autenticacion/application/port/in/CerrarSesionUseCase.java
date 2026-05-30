package com.servify.autenticacion.application.port.in;

import com.servify.autenticacion.application.dto.CerrarSesionCommand;

public interface CerrarSesionUseCase {

    void cerrar(CerrarSesionCommand command);
}
