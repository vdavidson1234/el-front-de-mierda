package com.servify.autenticacion.application.service;

import com.servify.autenticacion.application.dto.CerrarSesionCommand;
import com.servify.autenticacion.application.port.in.CerrarSesionUseCase;
import com.servify.autenticacion.application.port.out.RefreshTokenRepositoryPort;
import com.servify.autenticacion.application.port.out.TokenProviderPort;
import com.servify.autenticacion.domain.model.RefreshToken;

import java.time.LocalDateTime;

public class CerrarSesionService implements CerrarSesionUseCase {

    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;
    private final TokenProviderPort tokenProviderPort;

    public CerrarSesionService(RefreshTokenRepositoryPort refreshTokenRepositoryPort,
                               TokenProviderPort tokenProviderPort) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public void cerrar(CerrarSesionCommand command) {
        // Cierra la sesión del usuario revocando el refresh token proporcionado.
        // - valida que el token pertenezca al usuario y que no esté ya revocado
        if (command == null) {
            throw new IllegalArgumentException("El comando de cierre de sesión no puede ser nulo");
        }
        
        if (command.getUsuarioId() == null || command.getRefreshToken() == null) {
            throw new IllegalArgumentException("El ID de usuario y el refresh token son obligatorios");
        }
        
        String tokenHash = tokenProviderPort.obtenerHashToken(command.getRefreshToken());
        RefreshToken refreshToken = obtenerRefreshToken(tokenHash);
        
        validarCierrePermitido(refreshToken, command);
        
        LocalDateTime fechaRevocacion = obtenerFechaActual();
        refreshToken.revocar(fechaRevocacion);
        
        refreshTokenRepositoryPort.guardar(refreshToken);
    }

    protected RefreshToken obtenerRefreshToken(String refreshTokenHash) {
        if (refreshTokenHash == null) {
            throw new IllegalArgumentException("El hash del token no puede ser nulo");
        }
        
        return refreshTokenRepositoryPort.buscarPorTokenHash(refreshTokenHash)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El refresh token no es válido o ha sido revocado"));
    }

    protected void validarCierrePermitido(RefreshToken refreshToken,
                                          CerrarSesionCommand command) {
        if (!refreshToken.perteneceAUsuario(command.getUsuarioId())) {
            throw new IllegalArgumentException(
                    "El refresh token no corresponde al usuario indicado");
        }
        
        if (refreshToken.fueRevocado()) {
            throw new IllegalArgumentException(
                    "El refresh token ya ha sido revocado");
        }
    }

    protected LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }
}
