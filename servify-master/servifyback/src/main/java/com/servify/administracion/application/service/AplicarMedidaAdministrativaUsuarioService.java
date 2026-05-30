package com.servify.administracion.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.administracion.application.dto.AplicarMedidaAdministrativaUsuarioCommand;
import com.servify.administracion.application.dto.MedidaAdministrativaUsuarioResult;
import com.servify.administracion.application.port.in.AplicarMedidaAdministrativaUsuarioUseCase;
import com.servify.administracion.application.port.out.MedidaAdministrativaUsuarioRepositoryPort;
import com.servify.administracion.application.port.out.UsuarioAdministrablePort;
import com.servify.administracion.domain.model.MedidaAdministrativaUsuario;

public class AplicarMedidaAdministrativaUsuarioService implements AplicarMedidaAdministrativaUsuarioUseCase {

    private final MedidaAdministrativaUsuarioRepositoryPort medidaAdministrativaUsuarioRepositoryPort;
    private final UsuarioAdministrablePort usuarioAdministrablePort;

    public AplicarMedidaAdministrativaUsuarioService(MedidaAdministrativaUsuarioRepositoryPort medidaAdministrativaUsuarioRepositoryPort,
                                                    UsuarioAdministrablePort usuarioAdministrablePort) {
        this.medidaAdministrativaUsuarioRepositoryPort = medidaAdministrativaUsuarioRepositoryPort;
        this.usuarioAdministrablePort = usuarioAdministrablePort;
    }

    @Override
    public MedidaAdministrativaUsuarioResult aplicar(AplicarMedidaAdministrativaUsuarioCommand command) {
        // Aplica una medida administrativa a un usuario:
        // - valida permisos de administrador y existencia del usuario
        // - persiste la medida y notifica al adaptador correspondiente
        validarAdministrador(command.getAdministradorId());
        validarUsuarioAfectado(command.getUsuarioId());
        
        LocalDateTime fechaAplicacion = obtenerFechaActual();
        MedidaAdministrativaUsuario medida = construirMedida(command, fechaAplicacion);
        
        MedidaAdministrativaUsuario medidaPersistida = medidaAdministrativaUsuarioRepositoryPort.guardar(medida);
        
        usuarioAdministrablePort.aplicarMedida(command.getUsuarioId(), command.getTipoMedida(), command.getMotivo());
        
        return construirResultado(medidaPersistida);
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

    protected void validarUsuarioAfectado(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El ID del usuario afectado no puede ser nulo");
        }
        
        if (!usuarioAdministrablePort.existeUsuario(usuarioId)) {
            throw new IllegalArgumentException("El usuario afectado no existe");
        }
    }

    protected MedidaAdministrativaUsuario construirMedida(AplicarMedidaAdministrativaUsuarioCommand command,
                                                          LocalDateTime fechaAplicacion) {
        if (command.getTipoMedida() == null) {
            throw new IllegalArgumentException("El tipo de medida no puede ser nulo");
        }
        
        if (command.getMotivo() == null || command.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede ser nulo o vacío");
        }
        
        UUID medidaId = generarIdMedida();
        
        return new MedidaAdministrativaUsuario(
                medidaId,
                command.getUsuarioId(),
                command.getAdministradorId(),
                command.getTipoMedida(),
                command.getMotivo(),
                fechaAplicacion,
                command.getFechaFinVigencia(),
                true
        );
    }

    protected MedidaAdministrativaUsuarioResult construirResultado(MedidaAdministrativaUsuario medidaAdministrativaUsuario) {
        return MedidaAdministrativaUsuarioResult.builder()
                .id(medidaAdministrativaUsuario.getId())
                .usuarioId(medidaAdministrativaUsuario.getUsuarioId())
                .administradorId(medidaAdministrativaUsuario.getAdministradorId())
                .tipoMedida(medidaAdministrativaUsuario.getTipoMedida())
                .motivo(medidaAdministrativaUsuario.getMotivo())
                .fechaAplicacion(medidaAdministrativaUsuario.getFechaAplicacion())
                .fechaFinVigencia(medidaAdministrativaUsuario.getFechaFinVigencia())
                .activa(medidaAdministrativaUsuario.getActiva())
                .build();
    }

    protected UUID generarIdMedida() {
        return UUID.randomUUID();
    }

    protected LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }
}
