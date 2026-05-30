package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.CalificarServicioCommand;

/**
 Es el puerto de entrada del caso de uso mediante el cual el solicitante califica al prestador una vez finalizado el servicio.
 El MVP define que la calificación es simple, de 1 a 5 estrellas, y solo puede hacerse sobre una solicitud finalizada.
 */

public interface CalificarServicioUseCase {

    void calificar(CalificarServicioCommand command);
}