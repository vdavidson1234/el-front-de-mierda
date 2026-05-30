package com.servify.administracion.infrastructure.web;

import com.servify.administracion.application.dto.ConfiguracionGeneralResult;
import com.servify.administracion.application.dto.ModerarPublicacionCommand;
import com.servify.administracion.application.port.in.ModerarPublicacionUseCase;
import com.servify.administracion.application.port.in.ObtenerConfiguracionGeneralUseCase;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final ModerarPublicacionUseCase moderarPublicacionUseCase;
    private final ObtenerConfiguracionGeneralUseCase obtenerConfiguracionGeneralUseCase;

    public AdminApiController(
            ModerarPublicacionUseCase moderarPublicacionUseCase,
            ObtenerConfiguracionGeneralUseCase obtenerConfiguracionGeneralUseCase
    ) {
        this.moderarPublicacionUseCase = moderarPublicacionUseCase;
        this.obtenerConfiguracionGeneralUseCase = obtenerConfiguracionGeneralUseCase;
    }

    @PatchMapping("/publicaciones/{publicacionId}/moderacion")
    public ResponseEntity<Void> moderarPublicacion(
            @PathVariable UUID publicacionId,
            @RequestBody ModerarPublicacionRequest request
    ) {
        moderarPublicacionUseCase.moderar(
                new ModerarPublicacionCommand(
                        publicacionId,
                        request.administradorId,
                        request.estadoDestino,
                        request.motivo
                )
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/configuracion")
    public ResponseEntity<ConfiguracionGeneralResult> obtenerConfiguracion() {
        return obtenerConfiguracionGeneralUseCase.obtenerVigente()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static class ModerarPublicacionRequest {
        public UUID administradorId;
        public String estadoDestino;
        public String motivo;
    }
}
