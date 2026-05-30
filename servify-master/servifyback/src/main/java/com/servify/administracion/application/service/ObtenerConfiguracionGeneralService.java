package com.servify.administracion.application.service;

import com.servify.administracion.application.dto.ConfiguracionGeneralResult;
import com.servify.administracion.application.port.in.ObtenerConfiguracionGeneralUseCase;
import com.servify.administracion.application.port.out.ConfiguracionGeneralRepositoryPort;
import com.servify.administracion.domain.model.ConfiguracionGeneral;

import java.util.Optional;
import java.util.UUID;

public class ObtenerConfiguracionGeneralService implements ObtenerConfiguracionGeneralUseCase {

    private final ConfiguracionGeneralRepositoryPort configuracionGeneralRepositoryPort;

    public ObtenerConfiguracionGeneralService(ConfiguracionGeneralRepositoryPort configuracionGeneralRepositoryPort) {
        this.configuracionGeneralRepositoryPort = configuracionGeneralRepositoryPort;
    }

    @Override
    public Optional<ConfiguracionGeneralResult> obtenerVigente() {
        // Obtiene la configuración general vigente si existe.
        Optional<ConfiguracionGeneral> opt = this.configuracionGeneralRepositoryPort.obtenerVigente();
        return opt.map(this::construirResultado);
    }

    @Override
    public Optional<ConfiguracionGeneralResult> obtenerPorId(UUID configuracionGeneralId) {
        // Obtiene una configuración por su id.
        if (configuracionGeneralId == null) {
            throw new IllegalArgumentException("configuracionGeneralId no puede ser nulo");
        }
        Optional<ConfiguracionGeneral> opt = this.configuracionGeneralRepositoryPort.buscarPorId(configuracionGeneralId);
        return opt.map(this::construirResultado);
    }

    protected ConfiguracionGeneralResult construirResultado(ConfiguracionGeneral configuracionGeneral) {
        if (configuracionGeneral == null) {
            return null;
        }
        return ConfiguracionGeneralResult.builder()
                .id(configuracionGeneral.getId())
                .radioBusquedaInicialKm(configuracionGeneral.getRadioBusquedaInicialKm())
                .radioBusquedaExpansionKm(configuracionGeneral.getRadioBusquedaExpansionKm())
                .tiempoEsperaExpansionMinutos(configuracionGeneral.getTiempoEsperaExpansionMinutos())
                .validacionIdentidadRequerida(configuracionGeneral.getValidacionIdentidadRequerida())
                .precioBaseMinimoReferencia(configuracionGeneral.getPrecioBaseMinimoReferencia())
                .plataformaActiva(configuracionGeneral.getPlataformaActiva())
                .fechaUltimaActualizacion(configuracionGeneral.getFechaUltimaActualizacion())
                .build();
    }
}
