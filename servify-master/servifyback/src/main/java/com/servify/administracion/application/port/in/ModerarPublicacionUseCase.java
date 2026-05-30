package com.servify.administracion.application.port.in;

import com.servify.administracion.application.dto.ModerarPublicacionCommand;

public interface ModerarPublicacionUseCase {

    void moderar(ModerarPublicacionCommand command);
}
