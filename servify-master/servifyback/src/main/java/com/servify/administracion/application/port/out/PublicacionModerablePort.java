package com.servify.administracion.application.port.out;

import java.util.UUID;

public interface PublicacionModerablePort {

    boolean existePublicacion(UUID publicacionServicioId);

    void moderarPublicacion(UUID publicacionServicioId, String estadoDestino, String motivo);
}
