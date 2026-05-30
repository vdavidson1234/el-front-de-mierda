package com.servify.usuarios.application.port.out;

import com.servify.usuarios.domain.model.PerfilUsuario;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia y consultas del agregado PerfilUsuario.
 * Define el contrato que la infraestructura debe implementar.
 */
public interface PerfilUsuarioRepositoryPort {

    /**
     * Guarda o actualiza un perfil de usuario y devuelve la instancia persistida.
     */
    PerfilUsuario guardar(PerfilUsuario perfilUsuario);

    /**
     * Busca un perfil por su identificador unico.
     */
    Optional<PerfilUsuario> buscarPorId(UUID perfilUsuarioId);

    /**
     * Busca el perfil asociado a un usuario.
     */
    Optional<PerfilUsuario> buscarPorUsuarioId(UUID usuarioId);
}