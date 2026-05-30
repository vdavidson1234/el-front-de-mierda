package com.servify.autenticacion.application.port.out;

import com.servify.autenticacion.application.dto.TokenResult;

import java.util.UUID;

public interface TokenProviderPort {

    TokenResult generarAccessToken(UUID usuarioId, String emailAcceso);

    TokenResult generarRefreshToken(UUID usuarioId, String emailAcceso);

    String obtenerHashToken(String token);
}
