package com.servify.solicitudes.application.port.in;

import com.servify.solicitudes.application.dto.SolicitudRecibidaResult;
import java.util.List;
import java.util.UUID;

public interface ListarSolicitudesRecibidasDetalladasUseCase {

    List<SolicitudRecibidaResult> listarPorPrestadorId(UUID prestadorId);
}
