package com.servify.autenticacion.application.port.out;

import com.servify.autenticacion.domain.model.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepositoryPort {

    RefreshToken guardar(RefreshToken refreshToken);

    Optional<RefreshToken> buscarPorId(UUID refreshTokenId);

    Optional<RefreshToken> buscarPorTokenHash(String tokenHash);

    List<RefreshToken> buscarActivosPorUsuarioId(UUID usuarioId);
}
