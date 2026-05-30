package com.servify.usuarios.application.port.in;

import com.servify.usuarios.application.dto.CrearUsuarioCommand;
import com.servify.usuarios.application.dto.UsuarioResult;

public interface CrearUsuarioUseCase {

    UsuarioResult crear(CrearUsuarioCommand command);
}