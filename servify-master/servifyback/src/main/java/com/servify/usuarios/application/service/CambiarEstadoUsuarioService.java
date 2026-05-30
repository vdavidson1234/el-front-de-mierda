package com.servify.usuarios.application.service;

import java.util.UUID;

import com.servify.shared.domain.exception.NotFoundException;
import com.servify.shared.domain.exception.ValidationException;
import com.servify.usuarios.application.dto.CambiarEstadoUsuarioCommand;
import com.servify.usuarios.application.port.in.CambiarEstadoUsuarioUseCase;
import com.servify.usuarios.application.port.out.UsuarioRepositoryPort;
import com.servify.usuarios.domain.enumtype.EstadoUsuario;
import com.servify.usuarios.domain.model.Usuario;

// Es la implementacion del caso de uso que cambia el estado de un usuario.
// Coordina:
// - busqueda del usuario
// - validacion basica de entrada
// - aplicacion del cambio de estado en la entidad
// - persistencia del usuario actualizado
public class CambiarEstadoUsuarioService implements CambiarEstadoUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public CambiarEstadoUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public void cambiarEstado(CambiarEstadoUsuarioCommand command) {
        // Valida la entrada, recupera la entidad, ejecuta la transicion y persiste el cambio.
        if (command == null) {
            throw new ValidationException("El command de cambio de estado es obligatorio");
        }

        if (command.getUsuarioId() == null) {
            throw new ValidationException("El usuarioId es obligatorio");
        }

        if (command.getNuevoEstado() == null) {
            throw new ValidationException("El nuevo estado es obligatorio");
        }

        Usuario usuario = obtenerUsuarioExistente(command.getUsuarioId());
        aplicarNuevoEstado(usuario, command.getNuevoEstado());
        persistirUsuario(usuario);
    }

    protected Usuario obtenerUsuarioExistente(UUID usuarioId) {
        // Busca el usuario por id y falla si no existe.
        if (usuarioId == null) {
            throw new ValidationException("El usuarioId es obligatorio");
        }

        return usuarioRepositoryPort.buscarPorId(usuarioId)
                .orElseThrow(() -> new NotFoundException("No existe un usuario con el id informado"));
    }

    protected void aplicarNuevoEstado(Usuario usuario, EstadoUsuario nuevoEstado) {
        // Traduce el estado pedido al comportamiento correspondiente del dominio.
        if (usuario == null) {
            throw new ValidationException("El usuario es obligatorio");
        }

        if (nuevoEstado == null) {
            throw new ValidationException("El nuevo estado es obligatorio");
        }

        switch (nuevoEstado) {
            case ACTIVO -> usuario.activar();
            case SUSPENDIDO -> usuario.suspender();
            case BLOQUEADO -> usuario.bloquear();
            case INACTIVO -> usuario.desactivar();
        }
    }

    protected void persistirUsuario(Usuario usuario) {
        // Persiste el usuario actualizado mediante el puerto de salida.
        if (usuario == null) {
            throw new ValidationException("El usuario es obligatorio");
        }

        usuarioRepositoryPort.guardar(usuario);
    }
}
