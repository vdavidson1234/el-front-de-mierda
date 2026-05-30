package com.servify.autenticacion.infrastructure.web;

import com.servify.autenticacion.application.dto.CerrarSesionCommand;
import com.servify.autenticacion.application.dto.IniciarSesionCommand;
import com.servify.autenticacion.application.dto.RegistrarCredencialesCommand;
import com.servify.autenticacion.application.dto.RenovarTokenCommand;
import com.servify.autenticacion.application.dto.SesionResult;
import com.servify.autenticacion.application.port.in.CerrarSesionUseCase;
import com.servify.autenticacion.application.port.in.IniciarSesionUseCase;
import com.servify.autenticacion.application.port.in.RegistrarCredencialesUseCase;
import com.servify.autenticacion.application.port.in.RenovarTokenUseCase;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApiController {

    private final RegistrarCredencialesUseCase registrarCredencialesUseCase;
    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final RenovarTokenUseCase renovarTokenUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;

    public AuthApiController(
            RegistrarCredencialesUseCase registrarCredencialesUseCase,
            IniciarSesionUseCase iniciarSesionUseCase,
            RenovarTokenUseCase renovarTokenUseCase,
            CerrarSesionUseCase cerrarSesionUseCase
    ) {
        this.registrarCredencialesUseCase = registrarCredencialesUseCase;
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.renovarTokenUseCase = renovarTokenUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
    }

    @PostMapping("/credenciales")
    public ResponseEntity<Void> registrarCredenciales(@RequestBody RegistrarCredencialesRequest request) {
        registrarCredencialesUseCase.registrar(
                new RegistrarCredencialesCommand(
                        request.usuarioId,
                        request.emailAcceso,
                        request.passwordPlano
                )
        );
        return ResponseEntity
                .created(URI.create("/api/v1/auth/credenciales/" + request.usuarioId))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<SesionResult> iniciarSesion(@RequestBody IniciarSesionRequest request) {
        SesionResult result = iniciarSesionUseCase.iniciar(
                new IniciarSesionCommand(request.emailAcceso, request.passwordPlano)
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SesionResult> renovarToken(@RequestBody RenovarTokenRequest request) {
        SesionResult result = renovarTokenUseCase.renovar(new RenovarTokenCommand(request.refreshToken));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> cerrarSesion(@RequestBody CerrarSesionRequest request) {
        cerrarSesionUseCase.cerrar(new CerrarSesionCommand(request.usuarioId, request.refreshToken));
        return ResponseEntity.noContent().build();
    }

    public static class RegistrarCredencialesRequest {
        public UUID usuarioId;
        public String emailAcceso;
        public String passwordPlano;
    }

    public static class IniciarSesionRequest {
        public String emailAcceso;
        public String passwordPlano;
    }

    public static class RenovarTokenRequest {
        public String refreshToken;
    }

    public static class CerrarSesionRequest {
        public UUID usuarioId;
        public String refreshToken;
    }
}
