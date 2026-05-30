package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.CancelarSolicitudServicioCommand;

public interface CancelarSolicitudServicioUseCase {

    void cancelar(CancelarSolicitudServicioCommand command);
}