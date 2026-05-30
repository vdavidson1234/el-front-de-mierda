package com.servify.administracion.application.port.out;

import com.servify.administracion.domain.model.MedidaAdministrativaUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedidaAdministrativaUsuarioRepositoryPort {

    MedidaAdministrativaUsuario guardar(MedidaAdministrativaUsuario medidaAdministrativaUsuario);

    Optional<MedidaAdministrativaUsuario> buscarPorId(UUID medidaAdministrativaUsuarioId);

    List<MedidaAdministrativaUsuario> buscarPorUsuarioId(UUID usuarioId);

    List<MedidaAdministrativaUsuario> buscarActivasPorUsuarioId(UUID usuarioId);
}
