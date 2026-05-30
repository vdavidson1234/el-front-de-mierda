package com.servify.solicitudes.application.port.out;

import com.servify.solicitudes.domain.model.DistribucionSolicitud;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para la gestión de distribuciones de solicitudes.
 * guardar(...) para persistir cambios de estado y nuevas distribuciones
 * buscarPorId(...) porque es la consulta base para responder una distribución puntual
 * buscarPorSolicitudId(...) para reconstruir el estado general de una solicitud
 * buscarPorPrestadorId(...) porque el prestador necesita ver solicitudes recibidas
 * buscarActivasPorSolicitudId(...) porque después va a servir para cierre
 */

public interface DistribucionSolicitudRepositoryPort {

    DistribucionSolicitud guardar(DistribucionSolicitud distribucionSolicitud);

    Optional<DistribucionSolicitud> buscarPorId(UUID distribucionSolicitudId);

    List<DistribucionSolicitud> buscarPorSolicitudId(UUID solicitudId);

    List<DistribucionSolicitud> buscarPorPrestadorId(UUID prestadorId);

    List<DistribucionSolicitud> buscarActivasPorSolicitudId(UUID solicitudId);
}