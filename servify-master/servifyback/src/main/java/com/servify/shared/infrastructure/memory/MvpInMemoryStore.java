package com.servify.shared.infrastructure.memory;

import com.servify.administracion.domain.model.ConfiguracionGeneral;
import com.servify.administracion.domain.model.MedidaAdministrativaUsuario;
import com.servify.autenticacion.domain.model.CredencialAcceso;
import com.servify.autenticacion.domain.model.RefreshToken;
import com.servify.publicaciones.domain.model.CategoriaServicio;
import com.servify.publicaciones.domain.model.PublicacionServicio;
import com.servify.shared.domain.model.BaseEntity;
import com.servify.solicitudes.domain.model.AsignacionServicio;
import com.servify.solicitudes.domain.model.Calificacion;
import com.servify.solicitudes.domain.model.ConfirmacionFinalizacion;
import com.servify.solicitudes.domain.model.Contraoferta;
import com.servify.solicitudes.domain.model.DistribucionSolicitud;
import com.servify.solicitudes.domain.model.SolicitudServicio;
import com.servify.usuarios.domain.model.PerfilUsuario;
import com.servify.usuarios.domain.model.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MvpInMemoryStore {

    final Map<UUID, Usuario> usuarios = new ConcurrentHashMap<>();
    final Map<UUID, PerfilUsuario> perfiles = new ConcurrentHashMap<>();
    final Map<UUID, CategoriaServicio> categorias = new ConcurrentHashMap<>();
    final Map<UUID, PublicacionServicio> publicaciones = new ConcurrentHashMap<>();
    final Map<UUID, SolicitudServicio> solicitudes = new ConcurrentHashMap<>();
    final Map<UUID, DistribucionSolicitud> distribuciones = new ConcurrentHashMap<>();
    final Map<UUID, AsignacionServicio> asignaciones = new ConcurrentHashMap<>();
    final Map<UUID, ConfirmacionFinalizacion> confirmaciones = new ConcurrentHashMap<>();
    final Map<UUID, Calificacion> calificaciones = new ConcurrentHashMap<>();
    final Map<UUID, Contraoferta> contraofertas = new ConcurrentHashMap<>();
    final Map<UUID, CredencialAcceso> credenciales = new ConcurrentHashMap<>();
    final Map<UUID, RefreshToken> refreshTokens = new ConcurrentHashMap<>();
    final Map<UUID, ConfiguracionGeneral> configuraciones = new ConcurrentHashMap<>();
    final Map<UUID, MedidaAdministrativaUsuario> medidas = new ConcurrentHashMap<>();

    public MvpInMemoryStore() {
        ConfiguracionGeneral configuracion = new ConfiguracionGeneral(
                UUID.nameUUIDFromBytes("servify-configuracion-mvp".getBytes()),
                10,
                25,
                30,
                false,
                BigDecimal.ZERO,
                true,
                LocalDateTime.now()
        );
        touch(configuracion);
        configuraciones.put(configuracion.getId(), configuracion);
    }

    <T extends BaseEntity> T touch(T entity) {
        if (entity == null) {
            return null;
        }
        LocalDateTime ahora = LocalDateTime.now();
        if (entity.getFechaCreacion() == null) {
            entity.marcarCreacion(ahora);
        } else {
            entity.marcarModificacion(ahora);
        }
        return entity;
    }
}
