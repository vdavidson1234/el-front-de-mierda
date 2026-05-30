package com.servify.administracion.application.service;

import com.servify.administracion.application.dto.MedidaAdministrativaUsuarioResult;
import com.servify.administracion.application.port.in.ObtenerMedidasAdministrativasDeUsuarioUseCase;
import com.servify.administracion.application.port.out.MedidaAdministrativaUsuarioRepositoryPort;
import com.servify.administracion.domain.model.MedidaAdministrativaUsuario;

import java.util.List;
import java.util.UUID;

public class ObtenerMedidasAdministrativasDeUsuarioService implements ObtenerMedidasAdministrativasDeUsuarioUseCase {

    private final MedidaAdministrativaUsuarioRepositoryPort medidaAdministrativaUsuarioRepositoryPort;

    public ObtenerMedidasAdministrativasDeUsuarioService(MedidaAdministrativaUsuarioRepositoryPort medidaAdministrativaUsuarioRepositoryPort) {
        this.medidaAdministrativaUsuarioRepositoryPort = medidaAdministrativaUsuarioRepositoryPort;
    }

    @Override
    public List<MedidaAdministrativaUsuarioResult> obtenerPorUsuarioId(UUID usuarioId) {
        // Lista las medidas administrativas que afectan a un usuario dado.
        if (usuarioId == null) {
            throw new IllegalArgumentException("usuarioId no puede ser nulo");
        }
        List<MedidaAdministrativaUsuario> medidas = this.medidaAdministrativaUsuarioRepositoryPort.buscarPorUsuarioId(usuarioId);
        return medidas.stream().map(this::construirResultado).toList();
    }

    protected MedidaAdministrativaUsuarioResult construirResultado(MedidaAdministrativaUsuario medidaAdministrativaUsuario) {
        if (medidaAdministrativaUsuario == null) {
            return null;
        }
        return MedidaAdministrativaUsuarioResult.builder()
                .id(medidaAdministrativaUsuario.getId())
                .usuarioId(medidaAdministrativaUsuario.getUsuarioId())
                .administradorId(medidaAdministrativaUsuario.getAdministradorId())
                .tipoMedida(medidaAdministrativaUsuario.getTipoMedida())
                .motivo(medidaAdministrativaUsuario.getMotivo())
                .fechaAplicacion(medidaAdministrativaUsuario.getFechaAplicacion())
                .fechaFinVigencia(medidaAdministrativaUsuario.getFechaFinVigencia())
                .activa(medidaAdministrativaUsuario.getActiva())
                .build();
    }
}
