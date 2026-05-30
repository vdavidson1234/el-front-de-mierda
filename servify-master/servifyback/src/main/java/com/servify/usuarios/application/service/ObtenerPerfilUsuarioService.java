package com.servify.usuarios.application.service;

import java.util.Optional;
import java.util.UUID;

import com.servify.shared.domain.exception.ValidationException;
import com.servify.shared.domain.valueobject.Ubicacion;
import com.servify.usuarios.application.dto.PerfilUsuarioResult;
import com.servify.usuarios.application.dto.UbicacionResult;
import com.servify.usuarios.application.port.in.ObtenerPerfilUsuarioUseCase;
import com.servify.usuarios.application.port.out.PerfilUsuarioRepositoryPort;
import com.servify.usuarios.domain.model.PerfilUsuario;
import com.servify.usuarios.domain.valueobject.NombreCompleto;

/**
 * Caso de uso de consulta para obtener perfiles de usuario.
 */
public class ObtenerPerfilUsuarioService implements ObtenerPerfilUsuarioUseCase {

    private final PerfilUsuarioRepositoryPort perfilUsuarioRepositoryPort;

    public ObtenerPerfilUsuarioService(PerfilUsuarioRepositoryPort perfilUsuarioRepositoryPort) {
        this.perfilUsuarioRepositoryPort = perfilUsuarioRepositoryPort;
    }

    /**
     * Obtiene el perfil asociado a un usuario.
     *
     * Devuelve Optional.empty() cuando el usuario todavia no tiene perfil asociado.
     */
    @Override
    public Optional<PerfilUsuarioResult> obtenerPorUsuarioId(UUID usuarioId) {
        if (usuarioId == null) {
            throw new ValidationException("El usuarioId es obligatorio");
        }

        return perfilUsuarioRepositoryPort.buscarPorUsuarioId(usuarioId)
                .map(this::construirResultado);
    }

    /**
     * Obtiene un perfil por su propio identificador.
     *
     * Devuelve Optional.empty() si no existe un perfil con ese id.
     */
    @Override
    public Optional<PerfilUsuarioResult> obtenerPorPerfilId(UUID perfilId) {
        if (perfilId == null) {
            throw new ValidationException("El perfilId es obligatorio");
        }

        return perfilUsuarioRepositoryPort.buscarPorId(perfilId)
                .map(this::construirResultado);
    }

    protected PerfilUsuarioResult construirResultado(PerfilUsuario perfilUsuario) {
        // Mapea PerfilUsuario a PerfilUsuarioResult.
        if (perfilUsuario == null) {
            throw new ValidationException("El perfil es obligatorio");
        }

        NombreCompleto nombreCompleto = perfilUsuario.getNombreCompleto();

        return PerfilUsuarioResult.builder()
                .id(perfilUsuario.getId())
                .usuarioId(perfilUsuario.getUsuarioId())
                .nombre(nombreCompleto != null ? nombreCompleto.getNombre() : null)
                .apellido(nombreCompleto != null ? nombreCompleto.getApellido() : null)
                .edad(perfilUsuario.getEdad())
                .fotoPerfilUrl(perfilUsuario.getFotoPerfilUrl())
                .ubicacion(construirUbicacionResult(perfilUsuario.getUbicacion()))
                .descripcionPersonal(perfilUsuario.getDescripcionPersonal())
                .perfilCompleto(perfilUsuario.getPerfilCompleto())
                .build();
    }

    protected UbicacionResult construirUbicacionResult(Ubicacion ubicacion) {
        // Mapea Ubicacion del dominio al DTO de salida.
        if (ubicacion == null) {
            return null;
        }

        return new UbicacionResult(
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
}
