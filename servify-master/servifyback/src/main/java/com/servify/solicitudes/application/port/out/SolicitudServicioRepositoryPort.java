package com.servify.solicitudes.application.port.out;

import com.servify.solicitudes.domain.model.SolicitudServicio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitudServicioRepositoryPort {

    SolicitudServicio guardar(SolicitudServicio solicitudServicio);

    Optional<SolicitudServicio> buscarPorId(UUID solicitudId);

    List<SolicitudServicio> buscarPorSolicitanteId(UUID solicitanteId);
}