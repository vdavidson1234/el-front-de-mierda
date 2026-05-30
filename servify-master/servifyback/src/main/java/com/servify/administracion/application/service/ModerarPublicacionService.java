package com.servify.administracion.application.service;

import java.util.UUID;

import com.servify.administracion.application.dto.ModerarPublicacionCommand;
import com.servify.administracion.application.port.in.ModerarPublicacionUseCase;
import com.servify.administracion.application.port.out.PublicacionModerablePort;
import com.servify.administracion.application.port.out.UsuarioAdministrablePort;

public class ModerarPublicacionService implements ModerarPublicacionUseCase {

    private final PublicacionModerablePort publicacionModerablePort;
    private final UsuarioAdministrablePort usuarioAdministrablePort;

    public ModerarPublicacionService(PublicacionModerablePort publicacionModerablePort,
                                     UsuarioAdministrablePort usuarioAdministrablePort) {
        this.publicacionModerablePort = publicacionModerablePort;
        this.usuarioAdministrablePort = usuarioAdministrablePort;
    }

    @Override
    public void moderar(ModerarPublicacionCommand command) {
        // Modera una publicación: valida administrador y actualiza el estado de la publicación.
        validarAdministrador(command.getAdministradorId());
        validarPublicacion(command.getPublicacionServicioId());
        validarEstadoDestino(command.getEstadoDestino());
        
        publicacionModerablePort.moderarPublicacion(
                command.getPublicacionServicioId(),
                command.getEstadoDestino(),
                command.getMotivo()
        );
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

    protected void validarPublicacion(UUID publicacionServicioId) {
        if (publicacionServicioId == null) {
            throw new IllegalArgumentException("El ID de la publicación no puede ser nulo");
        }
        
        if (!publicacionModerablePort.existePublicacion(publicacionServicioId)) {
            throw new IllegalArgumentException("La publicación no existe");
        }
    }

    protected void validarEstadoDestino(String estadoDestino) {
        if (estadoDestino == null || estadoDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado destino no puede ser nulo o vacío");
        }
        
        // Estados válidos para moderación
        String[] estadosValidos = {"ACTIVA", "INACTIVA", "PAUSADA", "BLOQUEADA", "ELIMINADA"};
        
        boolean esValido = false;
        for (String estado : estadosValidos) {
            if (estado.equalsIgnoreCase(estadoDestino)) {
                esValido = true;
                break;
            }
        }
        
        if (!esValido) {
            throw new IllegalArgumentException(
                    "El estado destino debe ser uno de: ACTIVA, INACTIVA, PAUSADA, BLOQUEADA, ELIMINADA");
        }
    }
}
