package com.servify.autenticacion.application.service;

import com.servify.autenticacion.application.dto.RenovarTokenCommand;
import com.servify.autenticacion.application.dto.SesionResult;
import com.servify.autenticacion.application.dto.TokenResult;
import com.servify.autenticacion.application.port.in.RenovarTokenUseCase;
import com.servify.autenticacion.application.port.out.CredencialAccesoRepositoryPort;
import com.servify.autenticacion.application.port.out.RefreshTokenRepositoryPort;
import com.servify.autenticacion.application.port.out.TokenProviderPort;
import com.servify.autenticacion.application.port.out.UsuarioAutenticablePort;
import com.servify.autenticacion.domain.model.CredencialAcceso;
import com.servify.autenticacion.domain.model.RefreshToken;

import java.time.LocalDateTime;

public class RenovarTokenService implements RenovarTokenUseCase {

    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;
    private final CredencialAccesoRepositoryPort credencialAccesoRepositoryPort;
    private final TokenProviderPort tokenProviderPort;
    private final UsuarioAutenticablePort usuarioAutenticablePort;

    public RenovarTokenService(RefreshTokenRepositoryPort refreshTokenRepositoryPort,
                               CredencialAccesoRepositoryPort credencialAccesoRepositoryPort,
                               TokenProviderPort tokenProviderPort,
                               UsuarioAutenticablePort usuarioAutenticablePort) {
        this.refreshTokenRepositoryPort = refreshTokenRepositoryPort;
        this.credencialAccesoRepositoryPort = credencialAccesoRepositoryPort;
        this.tokenProviderPort = tokenProviderPort;
        this.usuarioAutenticablePort = usuarioAutenticablePort;
    }

    @Override
    public SesionResult renovar(RenovarTokenCommand command) {
        // Renueva tokens a partir de un refresh token válido.
        // - valida el refresh token, revoca el anterior y genera nuevos tokens
        if (command == null || command.getRefreshToken() == null || command.getRefreshToken().trim().isEmpty()) {
            throw new IllegalArgumentException("refreshToken no puede ser nulo o vacío");
        }

        LocalDateTime ahora = obtenerFechaActual();
        RefreshToken existente = obtenerRefreshToken(command.getRefreshToken());
        validarRefreshToken(existente, ahora);

        CredencialAcceso credencial = obtenerCredencial(existente);
        if (!usuarioAutenticablePort.puedeAutenticarse(credencial.getUsuarioId())) {
            throw new IllegalStateException("El usuario no puede autenticarse");
        }

        TokenResult nuevoAccess = tokenProviderPort.generarAccessToken(credencial.getUsuarioId(), credencial.getEmailAcceso());
        TokenResult nuevoRefresh = tokenProviderPort.generarRefreshToken(credencial.getUsuarioId(), credencial.getEmailAcceso());

        // Revocar refresh token anterior y persistir cambio
        existente.revocar(ahora);
        refreshTokenRepositoryPort.guardar(existente);

        RefreshToken refreshDominio = construirRefreshToken(credencial, nuevoRefresh);
        refreshTokenRepositoryPort.guardar(refreshDominio);

        return construirResultado(credencial, nuevoAccess, nuevoRefresh, ahora);
    }

    protected RefreshToken obtenerRefreshToken(String refreshTokenPlano) {
        if (refreshTokenPlano == null || refreshTokenPlano.trim().isEmpty()) {
            throw new IllegalArgumentException("refreshToken no puede ser nulo o vacío");
        }
        String hash = this.tokenProviderPort.obtenerHashToken(refreshTokenPlano);
        return this.refreshTokenRepositoryPort.buscarPorTokenHash(hash)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token no encontrado"));
    }

    protected CredencialAcceso obtenerCredencial(RefreshToken refreshToken) {
        if (refreshToken == null) {
            throw new IllegalArgumentException("refreshToken no puede ser nulo");
        }
        if (refreshToken.getCredencialAccesoId() == null) {
            throw new IllegalStateException("Refresh token sin credencial asociada");
        }
        return this.credencialAccesoRepositoryPort.buscarPorId(refreshToken.getCredencialAccesoId())
                .orElseThrow(() -> new IllegalStateException("Credencial de acceso asociada no encontrada"));
    }

    protected void validarRefreshToken(RefreshToken refreshToken,
                                       LocalDateTime fechaActual) {
        if (refreshToken == null) {
            throw new IllegalArgumentException("refreshToken no puede ser nulo");
        }
        if (!refreshToken.estaActivo()) {
            throw new IllegalStateException("Refresh token no está activo");
        }
        if (refreshToken.estaExpirado(fechaActual)) {
            throw new IllegalStateException("Refresh token expirado");
        }
        if (refreshToken.fueRevocado()) {
            throw new IllegalStateException("Refresh token revocado");
        }
    }

    protected RefreshToken construirRefreshToken(CredencialAcceso credencialAcceso,
                                                 TokenResult refreshToken) {
        if (credencialAcceso == null || refreshToken == null) {
            throw new IllegalArgumentException("credencialAcceso y refreshToken no pueden ser nulos");
        }
        String hash = this.tokenProviderPort.obtenerHashToken(refreshToken.getToken());
        return new RefreshToken(
                java.util.UUID.randomUUID(),
                credencialAcceso.getUsuarioId(),
                credencialAcceso.getId(),
                hash,
                refreshToken.getFechaEmision(),
                refreshToken.getFechaExpiracion(),
                null,
                true
        );
    }

    protected SesionResult construirResultado(CredencialAcceso credencialAcceso,
                                              TokenResult accessToken,
                                              TokenResult refreshToken,
                                              LocalDateTime fechaInicioSesion) {
        return SesionResult.builder()
            .usuarioId(credencialAcceso.getUsuarioId())
            .emailAcceso(credencialAcceso.getEmailAcceso())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .fechaInicioSesion(fechaInicioSesion)
            .build();
    }

    protected LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }
}
