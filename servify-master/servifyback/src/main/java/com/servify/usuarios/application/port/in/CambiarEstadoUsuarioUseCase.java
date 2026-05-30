package com.servify.usuarios.application.port.in;

import com.servify.usuarios.application.dto.CambiarEstadoUsuarioCommand;

public interface CambiarEstadoUsuarioUseCase {

    void cambiarEstado(CambiarEstadoUsuarioCommand command);
}