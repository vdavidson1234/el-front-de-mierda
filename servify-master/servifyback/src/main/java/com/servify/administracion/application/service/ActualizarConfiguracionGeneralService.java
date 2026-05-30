package com.servify.administracion.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.administracion.application.dto.ActualizarConfiguracionGeneralCommand;
import com.servify.administracion.application.dto.ConfiguracionGeneralResult;
import com.servify.administracion.application.port.in.ActualizarConfiguracionGeneralUseCase;
import com.servify.administracion.application.port.out.ConfiguracionGeneralRepositoryPort;
import com.servify.administracion.application.port.out.UsuarioAdministrablePort;
import com.servify.administracion.domain.model.ConfiguracionGeneral;

public class ActualizarConfiguracionGeneralService implements ActualizarConfiguracionGeneralUseCase {

    private final ConfiguracionGeneralRepositoryPort configuracionGeneralRepositoryPort;
    private final UsuarioAdministrablePort usuarioAdministrablePort;

    public ActualizarConfiguracionGeneralService(ConfiguracionGeneralRepositoryPort configuracionGeneralRepositoryPort,
                                                 UsuarioAdministrablePort usuarioAdministrablePort) {
        this.configuracionGeneralRepositoryPort = configuracionGeneralRepositoryPort;
        this.usuarioAdministrablePort = usuarioAdministrablePort;
    }

    @Override
    public ConfiguracionGeneralResult actualizar(ActualizarConfiguracionGeneralCommand command) {
        validarAdministrador(command.getAdministradorId());
        
        ConfiguracionGeneral configuracionGeneral = obtenerConfiguracion(command);
        LocalDateTime fechaActualizacion = obtenerFechaActual();
        
        aplicarActualizaciones(configuracionGeneral, command, fechaActualizacion);
        
        ConfiguracionGeneral configuracionActualizada = configuracionGeneralRepositoryPort.guardar(configuracionGeneral);
        
        return construirResultado(configuracionActualizada);
    }

    protected void validarAdministrador(UUID administradorId) {
        if (administradorId == null) {
            throw new IllegalArgumentException("El ID del administrador no puede ser nulo");
        }
        
        if (!usuarioAdministrablePort.existeUsuario(administradorId)) {
            throw new IllegalArgumentException("El usuario administrador no existe");
        }
        
        if (!usuarioAdministrablePort.esAdministrador(administradorId)) {
            throw new IllegalArgumentException("El usuario no tiene permisos de administrador");
        }
    }

    protected ConfiguracionGeneral obtenerConfiguracion(ActualizarConfiguracionGeneralCommand command) {
        UUID configuracionId = command.getConfiguracionGeneralId();
        
        if (configuracionId != null) {
            return configuracionGeneralRepositoryPort.buscarPorId(configuracionId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No se encontró configuración general con ID: " + configuracionId));
        }
        
        return configuracionGeneralRepositoryPort.obtenerVigente()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe configuración general vigente"));
    }

    protected void aplicarActualizaciones(ConfiguracionGeneral configuracionGeneral,
                                          ActualizarConfiguracionGeneralCommand command,
                                          LocalDateTime fechaActualizacion) {
        // Actualiza parámetros de búsqueda y validaciones básicas si están presentes
        if (command.getRadioBusquedaInicialKm() != null) {
            configuracionGeneral.actualizarRadioBusquedaInicialKm(command.getRadioBusquedaInicialKm());
        }
        
        if (command.getRadioBusquedaExpansionKm() != null) {
            configuracionGeneral.actualizarRadioBusquedaExpansionKm(command.getRadioBusquedaExpansionKm());
        }
        
        if (command.getTiempoEsperaExpansionMinutos() != null) {
            configuracionGeneral.actualizarTiempoEsperaExpansionMinutos(command.getTiempoEsperaExpansionMinutos());
        }
        
        if (command.getValidacionIdentidadRequerida() != null) {
            configuracionGeneral.actualizarValidacionIdentidadRequerida(command.getValidacionIdentidadRequerida());
        }
        
        if (command.getPrecioBaseMinimoReferencia() != null) {
            configuracionGeneral.actualizarPrecioBaseMinimoReferencia(command.getPrecioBaseMinimoReferencia());
        }
        
        // Habilita/deshabilita la plataforma según el comando.
        if (command.getPlataformaActiva() != null) {
            if (command.getPlataformaActiva()) {
                configuracionGeneral.activarPlataforma();
            } else {
                configuracionGeneral.desactivarPlataforma();
            }
        }

        // Registra la fecha de la última actualización en la entidad de dominio.
        configuracionGeneral.actualizarFechaUltimaActualizacion(fechaActualizacion);
    }

    protected ConfiguracionGeneralResult construirResultado(ConfiguracionGeneral configuracionGeneral) {
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

    protected LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }
}
