package com.servify.solicitudes.application.port.out;

import com.servify.solicitudes.domain.model.Contraoferta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**

   Es el puerto de salida que necesita el módulo solicitudes para persistir
   y consultar contraofertas emitidas por los prestadores.
 * guardar(...) para crear o actualizar contraofertas
 * buscarPorId(...) como consulta base
 * buscarPorDistribucionSolicitudId(...) para ver el historial de negociación de una distribución
 * buscarPendientePorDistribucionSolicitudId(...) porque normalmente no debería haber varias pendientes al mismo tiempo
 * buscarPorPrestadorId(...) por trazabilidad y consultas futuras
 */

public interface ContraofertaRepositoryPort {

    Contraoferta guardar(Contraoferta contraoferta);

    Optional<Contraoferta> buscarPorId(UUID contraofertaId);

    List<Contraoferta> buscarPorDistribucionSolicitudId(UUID distribucionSolicitudId);

    Optional<Contraoferta> buscarPendientePorDistribucionSolicitudId(UUID distribucionSolicitudId);

    List<Contraoferta> buscarPorPrestadorId(UUID prestadorId);
}