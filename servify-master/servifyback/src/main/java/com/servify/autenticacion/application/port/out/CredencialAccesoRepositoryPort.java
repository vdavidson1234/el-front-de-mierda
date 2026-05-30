package com.servify.autenticacion.application.port.out;

import com.servify.autenticacion.domain.model.CredencialAcceso;

import java.util.Optional;
import java.util.UUID;

public interface CredencialAccesoRepositoryPort {

    CredencialAcceso guardar(CredencialAcceso credencialAcceso);

    Optional<CredencialAcceso> buscarPorId(UUID credencialAccesoId);

    Optional<CredencialAcceso> buscarPorUsuarioId(UUID usuarioId);

    Optional<CredencialAcceso> buscarPorEmailAcceso(String emailAcceso);

    boolean existePorEmailAcceso(String emailAcceso);
}
