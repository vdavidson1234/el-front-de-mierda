package com.servify.solicitudes.application.service;

import com.servify.solicitudes.application.dto.AsignacionServicioResult;
import com.servify.solicitudes.application.dto.ContraofertaResult;
import com.servify.solicitudes.application.dto.EstadoAsignacionSolicitudResult;
import com.servify.solicitudes.application.port.in.ObtenerEstadoAsignacionSolicitudUseCase;
import com.servify.solicitudes.application.port.out.AsignacionServicioRepositoryPort;
import com.servify.solicitudes.application.port.out.ConfirmacionFinalizacionRepositoryPort;
import com.servify.solicitudes.application.port.out.ContraofertaRepositoryPort;
import com.servify.solicitudes.application.port.out.DistribucionSolicitudRepositoryPort;
import com.servify.solicitudes.application.port.out.SolicitudServicioRepositoryPort;
import com.servify.solicitudes.domain.model.AsignacionServicio;
import com.servify.solicitudes.domain.model.ConfirmacionFinalizacion;
import com.servify.solicitudes.domain.model.Contraoferta;
import com.servify.solicitudes.domain.model.DistribucionSolicitud;
import com.servify.solicitudes.domain.model.SolicitudServicio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ObtenerEstadoAsignacionSolicitudService implements ObtenerEstadoAsignacionSolicitudUseCase {

    private final SolicitudServicioRepositoryPort solicitudServicioRepositoryPort;
    private final DistribucionSolicitudRepositoryPort distribucionSolicitudRepositoryPort;
    private final AsignacionServicioRepositoryPort asignacionServicioRepositoryPort;
    private final ConfirmacionFinalizacionRepositoryPort confirmacionFinalizacionRepositoryPort;
    private final ContraofertaRepositoryPort contraofertaRepositoryPort;

    public ObtenerEstadoAsignacionSolicitudService(SolicitudServicioRepositoryPort solicitudServicioRepositoryPort,
                                                   DistribucionSolicitudRepositoryPort distribucionSolicitudRepositoryPort,
                                                   AsignacionServicioRepositoryPort asignacionServicioRepositoryPort,
                                                   ConfirmacionFinalizacionRepositoryPort confirmacionFinalizacionRepositoryPort,
                                                   ContraofertaRepositoryPort contraofertaRepositoryPort) {
        this.solicitudServicioRepositoryPort = solicitudServicioRepositoryPort;
        this.distribucionSolicitudRepositoryPort = distribucionSolicitudRepositoryPort;
        this.asignacionServicioRepositoryPort = asignacionServicioRepositoryPort;
        this.confirmacionFinalizacionRepositoryPort = confirmacionFinalizacionRepositoryPort;
        this.contraofertaRepositoryPort = contraofertaRepositoryPort;
    }

    @Override
    public EstadoAsignacionSolicitudResult obtenerEstado(UUID solicitudId) {
        // Obtiene el estado de asignación de una solicitud:
        // - solicitud principal
        // - asignación si existe
        // - distribuciones activas y contraofertas pendientes
        if (solicitudId == null) {
            throw new IllegalArgumentException("solicitudId no puede ser nulo");
        }
        SolicitudServicio solicitud = obtenerSolicitudExistente(solicitudId);
        Optional<AsignacionServicio> asignacion = obtenerAsignacion(solicitudId);
        List<DistribucionSolicitud> distribucionesActivas = obtenerDistribucionesActivas(solicitudId);
        List<Contraoferta> contraofertasPendientes = obtenerContraofertasPendientes(distribucionesActivas);
        List<ConfirmacionFinalizacion> confirmaciones = obtenerConfirmaciones(asignacion);

        return construirResultado(
            solicitud,
            asignacion,
            contraofertasPendientes,
            confirmaciones,
            distribucionesActivas == null ? 0 : distribucionesActivas.size()
        );
    }

    protected SolicitudServicio obtenerSolicitudExistente(UUID solicitudId) {
        if (solicitudId == null) {
            throw new IllegalArgumentException("solicitudId no puede ser nulo");
        }
        return this.solicitudServicioRepositoryPort.buscarPorId(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada: " + solicitudId));
    }

    protected Optional<AsignacionServicio> obtenerAsignacion(UUID solicitudId) {
        if (solicitudId == null) {
            return Optional.empty();
        }
        return this.asignacionServicioRepositoryPort.buscarPorSolicitudId(solicitudId);
    }

    protected List<DistribucionSolicitud> obtenerDistribucionesActivas(UUID solicitudId) {
        if (solicitudId == null) {
            return java.util.Collections.emptyList();
        }
        return this.distribucionSolicitudRepositoryPort.buscarActivasPorSolicitudId(solicitudId);
    }

    protected List<Contraoferta> obtenerContraofertasPendientes(List<DistribucionSolicitud> distribuciones) {
        java.util.List<Contraoferta> resultados = new java.util.ArrayList<>();
        if (distribuciones == null || distribuciones.isEmpty()) {
            return resultados;
        }
        for (DistribucionSolicitud d : distribuciones) {
            if (d == null) continue;
            this.contraofertaRepositoryPort.buscarPendientePorDistribucionSolicitudId(d.getId())
                    .ifPresent(resultados::add);
        }
        return resultados;
    }

    protected List<ConfirmacionFinalizacion> obtenerConfirmaciones(Optional<AsignacionServicio> asignacionServicio) {
        if (asignacionServicio == null || asignacionServicio.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return this.confirmacionFinalizacionRepositoryPort.buscarPorAsignacionServicioId(asignacionServicio.get().getId());
    }

    protected AsignacionServicioResult construirAsignacionResult(AsignacionServicio asignacionServicio) {
        if (asignacionServicio == null) {
            return null;
        }
        return AsignacionServicioResult.builder()
            .id(asignacionServicio.getId())
            .solicitudId(asignacionServicio.getSolicitudId())
            .distribucionSolicitudId(asignacionServicio.getDistribucionSolicitudId())
            .prestadorId(asignacionServicio.getPrestadorId())
            .publicacionServicioId(asignacionServicio.getPublicacionServicioId())
            .precioAcordado(asignacionServicio.getPrecioAcordado())
            .estado(asignacionServicio.getEstado())
            .fechaAsignacion(asignacionServicio.getFechaAsignacion())
            .fechaFinalizacion(asignacionServicio.getFechaFinalizacion())
            .build();
    }

    protected ContraofertaResult construirContraofertaResult(Contraoferta contraoferta) {
        if (contraoferta == null) {
            return null;
        }
        return ContraofertaResult.builder()
                .id(contraoferta.getId())
                .distribucionSolicitudId(contraoferta.getDistribucionSolicitudId())
                .prestadorId(contraoferta.getPrestadorId())
                .precioOriginal(contraoferta.getPrecioOriginal())
                .precioPropuesto(contraoferta.getPrecioPropuesto())
                .mensaje(contraoferta.getMensaje())
                .estado(contraoferta.getEstado())
                .fechaEmision(contraoferta.getFechaEmision())
                .fechaResolucion(contraoferta.getFechaResolucion())
                .build();
    }

    protected EstadoAsignacionSolicitudResult construirResultado(SolicitudServicio solicitudServicio,
                                                                 Optional<AsignacionServicio> asignacionServicio,
                                                                 List<Contraoferta> contraofertasPendientes,
                                     List<ConfirmacionFinalizacion> confirmaciones,
                                                                 Integer distribucionesActivas) {
        boolean confirmadoPorSolicitante = confirmaciones != null && confirmaciones.stream().anyMatch(ConfirmacionFinalizacion::esDelSolicitante);
        boolean confirmadoPorPrestador = confirmaciones != null && confirmaciones.stream().anyMatch(ConfirmacionFinalizacion::esDelPrestador);
        boolean finalizacionConfirmada = confirmadoPorSolicitante && confirmadoPorPrestador;

        EstadoAsignacionSolicitudResult.Builder builder = EstadoAsignacionSolicitudResult.builder()
                .solicitudId(solicitudServicio.getId())
                .solicitanteId(solicitudServicio.getSolicitanteId())
                .estadoSolicitud(solicitudServicio.getEstado())
            .distribucionesActivas(distribucionesActivas == null ? 0 : distribucionesActivas)
            .confirmadoPorSolicitante(confirmadoPorSolicitante)
            .confirmadoPorPrestador(confirmadoPorPrestador)
            .finalizacionConfirmada(finalizacionConfirmada);

        asignacionServicio.ifPresent(a -> builder.asignacion(construirAsignacionResult(a)));

        if (contraofertasPendientes != null && !contraofertasPendientes.isEmpty()) {
            java.util.List<ContraofertaResult> resultados = new java.util.ArrayList<>();
            for (Contraoferta c : contraofertasPendientes) {
                resultados.add(construirContraofertaResult(c));
            }
            builder.contraofertasPendientes(resultados);
        }

        return builder.build();
    }
}
