package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.AsignacionServicioResult;
import com.servify.solicitudes.application.dto.ConfirmarAsignacionSolicitudCommand;

public interface ConfirmarAsignacionSolicitudUseCase {

    AsignacionServicioResult confirmar(ConfirmarAsignacionSolicitudCommand command);
}
