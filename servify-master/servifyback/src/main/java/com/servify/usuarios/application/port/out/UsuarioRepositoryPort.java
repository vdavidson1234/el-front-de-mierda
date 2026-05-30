package com.servify.usuarios.application.port.out;

import com.servify.usuarios.domain.model.Usuario;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia y consultas del agregado Usuario.
 * Define el contrato que la infraestructura debe implementar.
 */
public interface UsuarioRepositoryPort {

    /**
     * Guarda o actualiza un usuario y devuelve la instancia persistida.
     */
    Usuario guardar(Usuario usuario);

    /**
     * Busca un usuario por su identificador unico.
     */
    Optional<Usuario> buscarPorId(UUID usuarioId);

    /**
     * Busca un usuario por su email de acceso.
     */
    Optional<Usuario> buscarPorEmail(String email);

    /**
     * Indica si existe un usuario registrado con el email provisto.
     */
    boolean existePorEmail(String email);
}