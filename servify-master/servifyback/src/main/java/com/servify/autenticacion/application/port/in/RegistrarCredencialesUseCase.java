package com.servify.autenticacion.application.port.in;

import com.servify.autenticacion.application.dto.RegistrarCredencialesCommand;

public interface RegistrarCredencialesUseCase {

    void registrar(RegistrarCredencialesCommand command);
}
