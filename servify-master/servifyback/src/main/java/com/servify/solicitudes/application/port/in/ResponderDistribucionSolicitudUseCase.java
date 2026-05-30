package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.ResponderDistribucionSolicitudCommand;

public interface ResponderDistribucionSolicitudUseCase {

    void responder(ResponderDistribucionSolicitudCommand command);
}