package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.SolicitudServicioResult;

import java.util.List;
import java.util.UUID;

public interface ListarSolicitudesRecibidasUseCase {

    List<SolicitudServicioResult> listarPorPrestadorId(UUID prestadorId);
}