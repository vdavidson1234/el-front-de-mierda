package com.servify.usuarios.application.port.in;

import com.servify.usuarios.application.dto.ActualizarPerfilUsuarioCommand;
import com.servify.usuarios.application.dto.PerfilUsuarioResult;

public interface ActualizarPerfilUsuarioUseCase {

    PerfilUsuarioResult actualizar(ActualizarPerfilUsuarioCommand command);
}