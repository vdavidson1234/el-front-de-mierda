package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.ConfirmarFinalizacionServicioCommand;

// Es el puerto de entrada del caso de uso mediante el cual una de las partes confirma la finalización del servicio.
// Lo dejo void porque es para comandar.

public interface ConfirmarFinalizacionServicioUseCase {

    void confirmar(ConfirmarFinalizacionServicioCommand command);
}