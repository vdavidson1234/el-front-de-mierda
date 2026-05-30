package com.servify.administracion.application.port.out;

import com.servify.administracion.domain.enumtype.TipoMedida;

import java.util.UUID;

public interface UsuarioAdministrablePort {

    boolean existeUsuario(UUID usuarioId);

    boolean esAdministrador(UUID usuarioId);

    void aplicarMedida(UUID usuarioId, TipoMedida tipoMedida, String motivo);
}
