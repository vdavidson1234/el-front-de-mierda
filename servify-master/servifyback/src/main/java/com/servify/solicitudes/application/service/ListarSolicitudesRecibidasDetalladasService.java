package com.servify.solicitudes.application.service;

import com.servify.shared.domain.valueobject.DisponibilidadHoraria;
import com.servify.shared.domain.valueobject.Ubicacion;
import com.servify.solicitudes.application.dto.DisponibilidadHorariaResult;
import com.servify.solicitudes.application.dto.SolicitudRecibidaResult;
import com.servify.solicitudes.application.dto.UbicacionSolicitudResult;
import com.servify.solicitudes.application.port.in.ListarSolicitudesRecibidasDetalladasUseCase;
import com.servify.solicitudes.application.port.out.DistribucionSolicitudRepositoryPort;
import com.servify.solicitudes.application.port.out.SolicitudServicioRepositoryPort;
import com.servify.solicitudes.domain.model.DistribucionSolicitud;
import com.servify.solicitudes.domain.model.SolicitudServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListarSolicitudesRecibidasDetalladasService implements ListarSolicitudesRecibidasDetalladasUseCase {

    private final DistribucionSolicitudRepositoryPort distribucionSolicitudRepositoryPort;
    private final SolicitudServicioRepositoryPort solicitudServicioRepositoryPort;

    public ListarSolicitudesRecibidasDetalladasService(
            DistribucionSolicitudRepositoryPort distribucionSolicitudRepositoryPort,
            SolicitudServicioRepositoryPort solicitudServicioRepositoryPort
    ) {
        this.distribucionSolicitudRepositoryPort = distribucionSolicitudRepositoryPort;
        this.solicitudServicioRepositoryPort = solicitudServicioRepositoryPort;
    }

    @Override
    public List<SolicitudRecibidaResult> listarPorPrestadorId(UUID prestadorId) {
        if (prestadorId == null) {
            throw new IllegalArgumentException("prestadorId no puede ser nulo");
        }

        List<SolicitudRecibidaResult> resultados = new ArrayList<>();
        for (DistribucionSolicitud distribucion : distribucionSolicitudRepositoryPort.buscarPorPrestadorId(prestadorId)) {
            solicitudServicioRepositoryPort.buscarPorId(distribucion.getSolicitudId())
                    .map(solicitud -> construirResultado(distribucion, solicitud))
                    .ifPresent(resultados::add);
        }
        return resultados;
    }

    private SolicitudRecibidaResult construirResultado(
            DistribucionSolicitud distribucion,
            SolicitudServicio solicitud
    ) {
        return SolicitudRecibidaResult.builder()
                .distribucionSolicitudId(distribucion.getId())
                .solicitudId(solicitud.getId())
                .publicacionServicioId(distribucion.getPublicacionServicioId())
                .prestadorId(distribucion.getPrestadorId())
                .solicitanteId(solicitud.getSolicitanteId())
                .categoriaServicioId(solicitud.getCategoriaServicioId())
                .modalidadServicio(solicitud.getModalidadServicio())
                .ubicacion(construirUbicacionResult(solicitud.getUbicacion()))
                .disponibilidadRequerida(construirDisponibilidadResult(solicitud.getDisponibilidadRequerida()))
                .descripcionNecesidad(solicitud.getDescripcionNecesidad())
                .precioReferencia(solicitud.getPrecioReferencia())
                .estadoDistribucion(distribucion.getEstado())
                .rondaDistribucion(distribucion.getRondaDistribucion())
                .fechaEnvio(distribucion.getFechaEnvio())
                .fechaExpiracion(distribucion.getFechaExpiracion())
                .build();
    }

    private UbicacionSolicitudResult construirUbicacionResult(Ubicacion ubicacion) {
        if (ubicacion == null) {
            return null;
        }
        return new UbicacionSolicitudResult(
                ubicacion.getPais(),
                ubicacion.getProvincia(),
                ubicacion.getCiudad(),
                ubicacion.getLocalidad(),
                ubicacion.getCalle(),
                ubicacion.getAltura(),
                ubicacion.getReferencia(),
                ubicacion.getLatitud(),
                ubicacion.getLongitud()
        );
    }

    private DisponibilidadHorariaResult construirDisponibilidadResult(DisponibilidadHoraria disponibilidadHoraria) {
        if (disponibilidadHoraria == null) {
            return null;
        }
        return new DisponibilidadHorariaResult(
                disponibilidadHoraria.getDiaSemana(),
                disponibilidadHoraria.getHoraDesde(),
                disponibilidadHoraria.getHoraHasta()
        );
    }
}
