package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.CrearSolicitudServicioCommand;
import com.servify.solicitudes.application.dto.SolicitudServicioResult;

/**
 Es el puerto de entrada del caso de uso de crear una solicitud de servicio.
 Define el contrato de la operación:
 - recibe un CrearSolicitudServicioCommand
 - devuelve un SolicitudServicioResult

 es un caso de uso central del sistema
 - valida datos importantes
 - crea una entidad principal del negocio
   y después va a disparar el flujo de distribución
 */


public interface CrearSolicitudServicioUseCase {

    SolicitudServicioResult crear(CrearSolicitudServicioCommand command);
}