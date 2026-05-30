package com.servify.shared.infrastructure.memory;

import com.servify.administracion.application.port.out.ConfiguracionGeneralRepositoryPort;
import com.servify.administracion.application.port.out.MedidaAdministrativaUsuarioRepositoryPort;
import com.servify.administracion.application.port.out.PublicacionModerablePort;
import com.servify.administracion.application.port.out.UsuarioAdministrablePort;
import com.servify.administracion.domain.enumtype.TipoMedida;
import com.servify.administracion.domain.model.ConfiguracionGeneral;
import com.servify.administracion.domain.model.MedidaAdministrativaUsuario;
import com.servify.autenticacion.application.dto.TokenResult;
import com.servify.autenticacion.application.port.out.CredencialAccesoRepositoryPort;
import com.servify.autenticacion.application.port.out.PasswordHasherPort;
import com.servify.autenticacion.application.port.out.RefreshTokenRepositoryPort;
import com.servify.autenticacion.application.port.out.TokenProviderPort;
import com.servify.autenticacion.application.port.out.UsuarioAutenticablePort;
import com.servify.autenticacion.domain.model.CredencialAcceso;
import com.servify.autenticacion.domain.model.RefreshToken;
import com.servify.publicaciones.application.port.out.CategoriaServicioRepositoryPort;
import com.servify.publicaciones.application.port.out.PublicacionServicioRepositoryPort;
import com.servify.publicaciones.application.port.out.UsuarioPublicadorPort;
import com.servify.publicaciones.domain.enumtype.EstadoCategoria;
import com.servify.publicaciones.domain.enumtype.EstadoPublicacion;
import com.servify.publicaciones.domain.model.CategoriaServicio;
import com.servify.publicaciones.domain.model.PublicacionServicio;
import com.servify.publicaciones.domain.service.PoliticaCompatibilidadPublicacion;
import com.servify.shared.domain.enumtype.ModalidadServicio;
import com.servify.shared.domain.valueobject.DisponibilidadHoraria;
import com.servify.shared.domain.valueobject.Ubicacion;
import com.servify.solicitudes.application.port.out.AsignacionServicioRepositoryPort;
import com.servify.solicitudes.application.port.out.CalificacionRepositoryPort;
import com.servify.solicitudes.application.port.out.ConfiguracionDistribucionPort;
import com.servify.solicitudes.application.port.out.ConfirmacionFinalizacionRepositoryPort;
import com.servify.solicitudes.application.port.out.ContraofertaRepositoryPort;
import com.servify.solicitudes.application.port.out.DistribucionSolicitudRepositoryPort;
import com.servify.solicitudes.application.port.out.PublicacionesCompatiblesPort;
import com.servify.solicitudes.application.port.out.SolicitudServicioRepositoryPort;
import com.servify.solicitudes.domain.enumtype.RolConfirmante;
import com.servify.solicitudes.domain.model.AsignacionServicio;
import com.servify.solicitudes.domain.model.Calificacion;
import com.servify.solicitudes.domain.model.ConfirmacionFinalizacion;
import com.servify.solicitudes.domain.model.Contraoferta;
import com.servify.solicitudes.domain.model.DistribucionSolicitud;
import com.servify.solicitudes.domain.model.SolicitudServicio;
import com.servify.usuarios.application.port.out.PerfilUsuarioRepositoryPort;
import com.servify.usuarios.application.port.out.UsuarioRepositoryPort;
import com.servify.usuarios.domain.model.PerfilUsuario;
import com.servify.usuarios.domain.model.Usuario;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "servify.adapters.memory.enabled", havingValue = "true", matchIfMissing = true)
public class MvpInMemoryAdapterConfiguration {

    @Bean
    MvpInMemoryStore mvpInMemoryStore() {
        return new MvpInMemoryStore();
    }

    @Bean
    UsuarioRepositoryPort usuarioRepositoryPort(MvpInMemoryStore store) {
        return new UsuarioMemoryRepository(store);
    }

    @Bean
    PerfilUsuarioRepositoryPort perfilUsuarioRepositoryPort(MvpInMemoryStore store) {
        return new PerfilMemoryRepository(store);
    }

    @Bean
    UsuarioPublicadorPort usuarioPublicadorPort(MvpInMemoryStore store) {
        return new UsuarioBridgeAdapter(store);
    }

    @Bean
    UsuarioAutenticablePort usuarioAutenticablePort(MvpInMemoryStore store) {
        return new UsuarioBridgeAdapter(store);
    }

    @Bean
    UsuarioAdministrablePort usuarioAdministrablePort(MvpInMemoryStore store) {
        return new UsuarioBridgeAdapter(store);
    }

    @Bean
    CategoriaServicioRepositoryPort categoriaServicioRepositoryPort(MvpInMemoryStore store) {
        return new CategoriaMemoryRepository(store);
    }

    @Bean
    PublicacionServicioRepositoryPort publicacionServicioRepositoryPort(MvpInMemoryStore store) {
        return new PublicacionMemoryRepository(store);
    }

    @Bean
    PublicacionModerablePort publicacionModerablePort(MvpInMemoryStore store) {
        return new PublicacionModerableMemoryAdapter(store);
    }

    @Bean
    SolicitudServicioRepositoryPort solicitudServicioRepositoryPort(MvpInMemoryStore store) {
        return new SolicitudMemoryRepository(store);
    }

    @Bean
    DistribucionSolicitudRepositoryPort distribucionSolicitudRepositoryPort(MvpInMemoryStore store) {
        return new DistribucionMemoryRepository(store);
    }

    @Bean
    AsignacionServicioRepositoryPort asignacionServicioRepositoryPort(MvpInMemoryStore store) {
        return new AsignacionMemoryRepository(store);
    }

    @Bean
    ConfirmacionFinalizacionRepositoryPort confirmacionFinalizacionRepositoryPort(MvpInMemoryStore store) {
        return new ConfirmacionMemoryRepository(store);
    }

    @Bean
    CalificacionRepositoryPort calificacionRepositoryPort(MvpInMemoryStore store) {
        return new CalificacionMemoryRepository(store);
    }

    @Bean
    ContraofertaRepositoryPort contraofertaRepositoryPort(MvpInMemoryStore store) {
        return new ContraofertaMemoryRepository(store);
    }

    @Bean
    CredencialAccesoRepositoryPort credencialAccesoRepositoryPort(MvpInMemoryStore store) {
        return new CredencialMemoryRepository(store);
    }

    @Bean
    RefreshTokenRepositoryPort refreshTokenRepositoryPort(MvpInMemoryStore store) {
        return new RefreshTokenMemoryRepository(store);
    }

    @Bean
    PublicacionesCompatiblesPort publicacionesCompatiblesPort(
            MvpInMemoryStore store,
            PoliticaCompatibilidadPublicacion politicaCompatibilidadPublicacion
    ) {
        return new PublicacionesCompatiblesMemoryAdapter(store, politicaCompatibilidadPublicacion);
    }

    @Bean
    ConfiguracionDistribucionPort configuracionDistribucionPort(MvpInMemoryStore store) {
        return new ConfiguracionMemoryAdapter(store);
    }

    @Bean
    ConfiguracionGeneralRepositoryPort configuracionGeneralRepositoryPort(MvpInMemoryStore store) {
        return new ConfiguracionMemoryAdapter(store);
    }

    @Bean
    MedidaAdministrativaUsuarioRepositoryPort medidaAdministrativaUsuarioRepositoryPort(MvpInMemoryStore store) {
        return new MedidaMemoryRepository(store);
    }

    @Bean
    PasswordHasherPort passwordHasherPort() {
        return new Sha256PasswordHasher();
    }

    @Bean
    TokenProviderPort tokenProviderPort() {
        return new SimpleTokenProvider();
    }

    private static class UsuarioMemoryRepository implements UsuarioRepositoryPort {

        private final MvpInMemoryStore store;

        private UsuarioMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public Usuario guardar(Usuario usuario) {
            store.touch(usuario);
            store.usuarios.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> buscarPorId(UUID usuarioId) {
            return Optional.ofNullable(store.usuarios.get(usuarioId));
        }

        @Override
        public Optional<Usuario> buscarPorEmail(String email) {
            return store.usuarios.values().stream()
                    .filter(usuario -> usuario.getContacto() != null)
                    .filter(usuario -> equalsIgnoreCase(usuario.getContacto().getEmail(), email))
                    .findFirst();
        }

        @Override
        public boolean existePorEmail(String email) {
            return buscarPorEmail(email).isPresent();
        }
    }

    private static class PerfilMemoryRepository implements PerfilUsuarioRepositoryPort {

        private final MvpInMemoryStore store;

        private PerfilMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public PerfilUsuario guardar(PerfilUsuario perfilUsuario) {
            store.touch(perfilUsuario);
            store.perfiles.put(perfilUsuario.getId(), perfilUsuario);
            return perfilUsuario;
        }

        @Override
        public Optional<PerfilUsuario> buscarPorId(UUID perfilUsuarioId) {
            return Optional.ofNullable(store.perfiles.get(perfilUsuarioId));
        }

        @Override
        public Optional<PerfilUsuario> buscarPorUsuarioId(UUID usuarioId) {
            return store.perfiles.values().stream()
                    .filter(perfil -> Objects.equals(perfil.getUsuarioId(), usuarioId))
                    .findFirst();
        }
    }

    private static class UsuarioBridgeAdapter implements UsuarioPublicadorPort, UsuarioAutenticablePort, UsuarioAdministrablePort {

        private final MvpInMemoryStore store;

        private UsuarioBridgeAdapter(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public boolean existeUsuario(UUID usuarioId) {
            return store.usuarios.containsKey(usuarioId);
        }

        @Override
        public boolean puedePublicarServicios(UUID usuarioId) {
            return Optional.ofNullable(store.usuarios.get(usuarioId))
                    .map(Usuario::puedePublicarServicios)
                    .orElse(false);
        }

        @Override
        public boolean puedeAutenticarse(UUID usuarioId) {
            return Optional.ofNullable(store.usuarios.get(usuarioId))
                    .map(Usuario::estaActivo)
                    .orElse(false);
        }

        @Override
        public boolean coincideEmailPrincipal(UUID usuarioId, String email) {
            return Optional.ofNullable(store.usuarios.get(usuarioId))
                    .map(Usuario::getContacto)
                    .map(contacto -> equalsIgnoreCase(contacto.getEmail(), email))
                    .orElse(false);
        }

        @Override
        public boolean esAdministrador(UUID usuarioId) {
            return Optional.ofNullable(store.usuarios.get(usuarioId))
                    .map(Usuario::esAdmin)
                    .orElse(false);
        }

        @Override
        public void aplicarMedida(UUID usuarioId, TipoMedida tipoMedida, String motivo) {
            Usuario usuario = store.usuarios.get(usuarioId);
            if (usuario == null || tipoMedida == null) {
                return;
            }
            if (TipoMedida.BLOQUEO.equals(tipoMedida)) {
                usuario.bloquear();
            } else if (TipoMedida.SUSPENSION.equals(tipoMedida)) {
                usuario.suspender();
            }
            store.touch(usuario);
            store.usuarios.put(usuario.getId(), usuario);
        }
    }

    private static class CategoriaMemoryRepository implements CategoriaServicioRepositoryPort {

        private final MvpInMemoryStore store;

        private CategoriaMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public CategoriaServicio guardar(CategoriaServicio categoriaServicio) {
            store.touch(categoriaServicio);
            store.categorias.put(categoriaServicio.getId(), categoriaServicio);
            return categoriaServicio;
        }

        @Override
        public Optional<CategoriaServicio> buscarPorId(UUID categoriaServicioId) {
            return Optional.ofNullable(store.categorias.get(categoriaServicioId));
        }

        @Override
        public Optional<CategoriaServicio> buscarPorNombre(String nombre) {
            return store.categorias.values().stream()
                    .filter(categoria -> equalsIgnoreCase(categoria.getNombre(), nombre))
                    .findFirst();
        }

        @Override
        public List<CategoriaServicio> listarActivas() {
            return store.categorias.values().stream()
                    .filter(CategoriaServicio::estaActiva)
                    .sorted(Comparator.comparing(CategoriaServicio::getNombre, String.CASE_INSENSITIVE_ORDER))
                    .toList();
        }

        @Override
        public boolean existePorNombre(String nombre) {
            return buscarPorNombre(nombre).isPresent();
        }
    }

    private static class PublicacionMemoryRepository implements PublicacionServicioRepositoryPort {

        private final MvpInMemoryStore store;

        private PublicacionMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public PublicacionServicio guardar(PublicacionServicio publicacionServicio) {
            store.touch(publicacionServicio);
            store.publicaciones.put(publicacionServicio.getId(), publicacionServicio);
            return publicacionServicio;
        }

        @Override
        public Optional<PublicacionServicio> buscarPorId(UUID publicacionServicioId) {
            return Optional.ofNullable(store.publicaciones.get(publicacionServicioId));
        }

        @Override
        public List<PublicacionServicio> buscarPorUsuarioId(UUID usuarioId) {
            return store.publicaciones.values().stream()
                    .filter(publicacion -> Objects.equals(publicacion.getUsuarioId(), usuarioId))
                    .toList();
        }

        @Override
        public List<PublicacionServicio> buscarActivas() {
            return store.publicaciones.values().stream()
                    .filter(PublicacionServicio::estaActiva)
                    .toList();
        }

        @Override
        public List<PublicacionServicio> buscarActivasPorCategoria(UUID categoriaServicioId) {
            return store.publicaciones.values().stream()
                    .filter(PublicacionServicio::estaActiva)
                    .filter(publicacion -> publicacion.getCategoriaServicio() != null)
                    .filter(publicacion -> Objects.equals(publicacion.getCategoriaServicio().getId(), categoriaServicioId))
                    .toList();
        }

        @Override
        public boolean existePorUsuarioIdYTitulo(UUID usuarioId, String titulo) {
            return store.publicaciones.values().stream()
                    .filter(publicacion -> Objects.equals(publicacion.getUsuarioId(), usuarioId))
                    .anyMatch(publicacion -> equalsIgnoreCase(publicacion.getTitulo(), titulo));
        }
    }

    private static class PublicacionModerableMemoryAdapter implements PublicacionModerablePort {

        private final MvpInMemoryStore store;

        private PublicacionModerableMemoryAdapter(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public boolean existePublicacion(UUID publicacionServicioId) {
            return store.publicaciones.containsKey(publicacionServicioId);
        }

        @Override
        public void moderarPublicacion(UUID publicacionServicioId, String estadoDestino, String motivo) {
            PublicacionServicio publicacion = store.publicaciones.get(publicacionServicioId);
            if (publicacion == null) {
                return;
            }
            EstadoPublicacion destino = EstadoPublicacion.valueOf(estadoDestino.trim().toUpperCase());
            PublicacionServicio actualizada = switch (destino) {
                case ACTIVA -> {
                    publicacion.activar();
                    yield publicacion;
                }
                case INACTIVA -> {
                    publicacion.desactivar();
                    yield publicacion;
                }
                case PAUSADA -> {
                    publicacion.pausar();
                    yield publicacion;
                }
                case ELIMINADA -> {
                    publicacion.eliminar();
                    yield publicacion;
                }
                case BLOQUEADA -> copiarConEstado(publicacion, EstadoPublicacion.BLOQUEADA);
            };
            store.touch(actualizada);
            store.publicaciones.put(actualizada.getId(), actualizada);
        }

        private PublicacionServicio copiarConEstado(PublicacionServicio publicacion, EstadoPublicacion estado) {
            PublicacionServicio copia = new PublicacionServicio(
                    publicacion.getId(),
                    publicacion.getUsuarioId(),
                    publicacion.getCategoriaServicio(),
                    publicacion.getTitulo(),
                    publicacion.getDescripcion(),
                    publicacion.getModalidadServicio(),
                    publicacion.getUbicacion(),
                    publicacion.getDisponibilidadesHorarias(),
                    publicacion.getPrecioBase(),
                    estado
            );
            if (publicacion.getFechaCreacion() != null) {
                copia.marcarCreacion(publicacion.getFechaCreacion());
            }
            return copia;
        }
    }

    private static class SolicitudMemoryRepository implements SolicitudServicioRepositoryPort {

        private final MvpInMemoryStore store;

        private SolicitudMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public SolicitudServicio guardar(SolicitudServicio solicitudServicio) {
            store.touch(solicitudServicio);
            store.solicitudes.put(solicitudServicio.getId(), solicitudServicio);
            return solicitudServicio;
        }

        @Override
        public Optional<SolicitudServicio> buscarPorId(UUID solicitudId) {
            return Optional.ofNullable(store.solicitudes.get(solicitudId));
        }

        @Override
        public List<SolicitudServicio> buscarPorSolicitanteId(UUID solicitanteId) {
            return store.solicitudes.values().stream()
                    .filter(solicitud -> Objects.equals(solicitud.getSolicitanteId(), solicitanteId))
                    .toList();
        }
    }

    private static class DistribucionMemoryRepository implements DistribucionSolicitudRepositoryPort {

        private final MvpInMemoryStore store;

        private DistribucionMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public DistribucionSolicitud guardar(DistribucionSolicitud distribucionSolicitud) {
            store.touch(distribucionSolicitud);
            store.distribuciones.put(distribucionSolicitud.getId(), distribucionSolicitud);
            return distribucionSolicitud;
        }

        @Override
        public Optional<DistribucionSolicitud> buscarPorId(UUID distribucionSolicitudId) {
            return Optional.ofNullable(store.distribuciones.get(distribucionSolicitudId));
        }

        @Override
        public List<DistribucionSolicitud> buscarPorSolicitudId(UUID solicitudId) {
            return store.distribuciones.values().stream()
                    .filter(distribucion -> Objects.equals(distribucion.getSolicitudId(), solicitudId))
                    .toList();
        }

        @Override
        public List<DistribucionSolicitud> buscarPorPrestadorId(UUID prestadorId) {
            return store.distribuciones.values().stream()
                    .filter(distribucion -> Objects.equals(distribucion.getPrestadorId(), prestadorId))
                    .toList();
        }

        @Override
        public List<DistribucionSolicitud> buscarActivasPorSolicitudId(UUID solicitudId) {
            return buscarPorSolicitudId(solicitudId).stream()
                    .filter(distribucion -> distribucion.estaEnviada()
                            || distribucion.estaAceptada()
                            || distribucion.estaContraofertada())
                    .toList();
        }
    }

    private static class AsignacionMemoryRepository implements AsignacionServicioRepositoryPort {

        private final MvpInMemoryStore store;

        private AsignacionMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public AsignacionServicio guardar(AsignacionServicio asignacionServicio) {
            store.touch(asignacionServicio);
            store.asignaciones.put(asignacionServicio.getId(), asignacionServicio);
            return asignacionServicio;
        }

        @Override
        public Optional<AsignacionServicio> buscarPorId(UUID asignacionServicioId) {
            return Optional.ofNullable(store.asignaciones.get(asignacionServicioId));
        }

        @Override
        public Optional<AsignacionServicio> buscarPorSolicitudId(UUID solicitudId) {
            return store.asignaciones.values().stream()
                    .filter(asignacion -> Objects.equals(asignacion.getSolicitudId(), solicitudId))
                    .findFirst();
        }

        @Override
        public List<AsignacionServicio> buscarPorPrestadorId(UUID prestadorId) {
            return store.asignaciones.values().stream()
                    .filter(asignacion -> Objects.equals(asignacion.getPrestadorId(), prestadorId))
                    .toList();
        }

        @Override
        public List<AsignacionServicio> buscarPorSolicitanteId(UUID solicitanteId) {
            return store.asignaciones.values().stream()
                    .filter(asignacion -> Optional.ofNullable(store.solicitudes.get(asignacion.getSolicitudId()))
                            .map(solicitud -> Objects.equals(solicitud.getSolicitanteId(), solicitanteId))
                            .orElse(false))
                    .toList();
        }
    }

    private static class ConfirmacionMemoryRepository implements ConfirmacionFinalizacionRepositoryPort {

        private final MvpInMemoryStore store;

        private ConfirmacionMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public ConfirmacionFinalizacion guardar(ConfirmacionFinalizacion confirmacionFinalizacion) {
            store.touch(confirmacionFinalizacion);
            store.confirmaciones.put(confirmacionFinalizacion.getId(), confirmacionFinalizacion);
            return confirmacionFinalizacion;
        }

        @Override
        public Optional<ConfirmacionFinalizacion> buscarPorId(UUID confirmacionFinalizacionId) {
            return Optional.ofNullable(store.confirmaciones.get(confirmacionFinalizacionId));
        }

        @Override
        public List<ConfirmacionFinalizacion> buscarPorSolicitudId(UUID solicitudId) {
            return store.confirmaciones.values().stream()
                    .filter(confirmacion -> Objects.equals(confirmacion.getSolicitudId(), solicitudId))
                    .toList();
        }

        @Override
        public List<ConfirmacionFinalizacion> buscarPorAsignacionServicioId(UUID asignacionServicioId) {
            return store.confirmaciones.values().stream()
                    .filter(confirmacion -> Objects.equals(confirmacion.getAsignacionServicioId(), asignacionServicioId))
                    .toList();
        }

        @Override
        public Optional<ConfirmacionFinalizacion> buscarPorAsignacionServicioIdYRolConfirmante(
                UUID asignacionServicioId,
                RolConfirmante rolConfirmante
        ) {
            return store.confirmaciones.values().stream()
                    .filter(confirmacion -> Objects.equals(confirmacion.getAsignacionServicioId(), asignacionServicioId))
                    .filter(confirmacion -> confirmacion.getRolConfirmante() == rolConfirmante)
                    .findFirst();
        }

        @Override
        public List<ConfirmacionFinalizacion> buscarPorConfirmanteId(UUID confirmanteId) {
            return store.confirmaciones.values().stream()
                    .filter(confirmacion -> Objects.equals(confirmacion.getConfirmanteId(), confirmanteId))
                    .toList();
        }
    }

    private static class CalificacionMemoryRepository implements CalificacionRepositoryPort {

        private final MvpInMemoryStore store;

        private CalificacionMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public Calificacion guardar(Calificacion calificacion) {
            store.touch(calificacion);
            store.calificaciones.put(calificacion.getId(), calificacion);
            return calificacion;
        }

        @Override
        public Optional<Calificacion> buscarPorId(UUID calificacionId) {
            return Optional.ofNullable(store.calificaciones.get(calificacionId));
        }

        @Override
        public Optional<Calificacion> buscarPorSolicitudId(UUID solicitudId) {
            return store.calificaciones.values().stream()
                    .filter(calificacion -> Objects.equals(calificacion.getSolicitudId(), solicitudId))
                    .findFirst();
        }

        @Override
        public List<Calificacion> buscarPorPrestadorId(UUID prestadorId) {
            return store.calificaciones.values().stream()
                    .filter(calificacion -> Objects.equals(calificacion.getPrestadorId(), prestadorId))
                    .toList();
        }

        @Override
        public List<Calificacion> buscarPorSolicitanteId(UUID solicitanteId) {
            return store.calificaciones.values().stream()
                    .filter(calificacion -> Objects.equals(calificacion.getSolicitanteId(), solicitanteId))
                    .toList();
        }
    }

    private static class ContraofertaMemoryRepository implements ContraofertaRepositoryPort {

        private final MvpInMemoryStore store;

        private ContraofertaMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public Contraoferta guardar(Contraoferta contraoferta) {
            store.touch(contraoferta);
            store.contraofertas.put(contraoferta.getId(), contraoferta);
            return contraoferta;
        }

        @Override
        public Optional<Contraoferta> buscarPorId(UUID contraofertaId) {
            return Optional.ofNullable(store.contraofertas.get(contraofertaId));
        }

        @Override
        public List<Contraoferta> buscarPorDistribucionSolicitudId(UUID distribucionSolicitudId) {
            return store.contraofertas.values().stream()
                    .filter(contraoferta -> Objects.equals(contraoferta.getDistribucionSolicitudId(), distribucionSolicitudId))
                    .toList();
        }

        @Override
        public Optional<Contraoferta> buscarPendientePorDistribucionSolicitudId(UUID distribucionSolicitudId) {
            return buscarPorDistribucionSolicitudId(distribucionSolicitudId).stream()
                    .filter(Contraoferta::estaPendiente)
                    .findFirst();
        }

        @Override
        public List<Contraoferta> buscarPorPrestadorId(UUID prestadorId) {
            return store.contraofertas.values().stream()
                    .filter(contraoferta -> Objects.equals(contraoferta.getPrestadorId(), prestadorId))
                    .toList();
        }
    }

    private static class CredencialMemoryRepository implements CredencialAccesoRepositoryPort {

        private final MvpInMemoryStore store;

        private CredencialMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public CredencialAcceso guardar(CredencialAcceso credencialAcceso) {
            store.touch(credencialAcceso);
            store.credenciales.put(credencialAcceso.getId(), credencialAcceso);
            return credencialAcceso;
        }

        @Override
        public Optional<CredencialAcceso> buscarPorId(UUID credencialAccesoId) {
            return Optional.ofNullable(store.credenciales.get(credencialAccesoId));
        }

        @Override
        public Optional<CredencialAcceso> buscarPorUsuarioId(UUID usuarioId) {
            return store.credenciales.values().stream()
                    .filter(credencial -> Objects.equals(credencial.getUsuarioId(), usuarioId))
                    .findFirst();
        }

        @Override
        public Optional<CredencialAcceso> buscarPorEmailAcceso(String emailAcceso) {
            return store.credenciales.values().stream()
                    .filter(credencial -> credencial.usaEmail(emailAcceso))
                    .findFirst();
        }

        @Override
        public boolean existePorEmailAcceso(String emailAcceso) {
            return buscarPorEmailAcceso(emailAcceso).isPresent();
        }
    }

    private static class RefreshTokenMemoryRepository implements RefreshTokenRepositoryPort {

        private final MvpInMemoryStore store;

        private RefreshTokenMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public RefreshToken guardar(RefreshToken refreshToken) {
            store.touch(refreshToken);
            store.refreshTokens.put(refreshToken.getId(), refreshToken);
            return refreshToken;
        }

        @Override
        public Optional<RefreshToken> buscarPorId(UUID refreshTokenId) {
            return Optional.ofNullable(store.refreshTokens.get(refreshTokenId));
        }

        @Override
        public Optional<RefreshToken> buscarPorTokenHash(String tokenHash) {
            return store.refreshTokens.values().stream()
                    .filter(refreshToken -> Objects.equals(refreshToken.getTokenHash(), tokenHash))
                    .findFirst();
        }

        @Override
        public List<RefreshToken> buscarActivosPorUsuarioId(UUID usuarioId) {
            return store.refreshTokens.values().stream()
                    .filter(refreshToken -> Objects.equals(refreshToken.getUsuarioId(), usuarioId))
                    .filter(RefreshToken::estaActivo)
                    .toList();
        }
    }

    private static class PublicacionesCompatiblesMemoryAdapter implements PublicacionesCompatiblesPort {

        private final MvpInMemoryStore store;
        private final PoliticaCompatibilidadPublicacion politicaCompatibilidadPublicacion;

        private PublicacionesCompatiblesMemoryAdapter(
                MvpInMemoryStore store,
                PoliticaCompatibilidadPublicacion politicaCompatibilidadPublicacion
        ) {
            this.store = store;
            this.politicaCompatibilidadPublicacion = politicaCompatibilidadPublicacion;
        }

        @Override
        public Map<UUID, UUID> buscarPublicacionesCompatibles(
                UUID solicitudId,
                UUID categoriaServicioId,
                ModalidadServicio modalidadRequerida,
                Ubicacion ubicacionRequerida,
                DisponibilidadHoraria disponibilidadRequerida,
                BigDecimal precioMaximo,
                Integer radioBusquedaKm
        ) {
            CategoriaServicio categoria = store.categorias.get(categoriaServicioId);
            Map<UUID, UUID> compatibles = new LinkedHashMap<>();
            for (PublicacionServicio publicacion : store.publicaciones.values()) {
                if (precioMaximo != null && publicacion.getPrecioBase() != null
                        && publicacion.getPrecioBase().compareTo(precioMaximo) > 0) {
                    continue;
                }
                if (politicaCompatibilidadPublicacion.esCompatible(
                        publicacion,
                        categoria,
                        modalidadRequerida,
                        ubicacionRequerida,
                        disponibilidadRequerida)) {
                    compatibles.put(publicacion.getId(), publicacion.getUsuarioId());
                }
            }
            return compatibles;
        }
    }

    private static class ConfiguracionMemoryAdapter implements ConfiguracionDistribucionPort, ConfiguracionGeneralRepositoryPort {

        private final MvpInMemoryStore store;

        private ConfiguracionMemoryAdapter(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public Integer obtenerRadioBusquedaInicialKm() {
            return vigente().map(ConfiguracionGeneral::getRadioBusquedaInicialKm).orElse(10);
        }

        @Override
        public Integer obtenerRadioBusquedaExpansionKm() {
            return vigente().map(ConfiguracionGeneral::getRadioBusquedaExpansionKm).orElse(25);
        }

        @Override
        public Integer obtenerTiempoEsperaExpansionMinutos() {
            return vigente().map(ConfiguracionGeneral::getTiempoEsperaExpansionMinutos).orElse(30);
        }

        @Override
        public BigDecimal obtenerPrecioBaseMinimoReferencia() {
            return vigente().map(ConfiguracionGeneral::getPrecioBaseMinimoReferencia).orElse(BigDecimal.ZERO);
        }

        @Override
        public ConfiguracionGeneral guardar(ConfiguracionGeneral configuracionGeneral) {
            store.touch(configuracionGeneral);
            store.configuraciones.put(configuracionGeneral.getId(), configuracionGeneral);
            return configuracionGeneral;
        }

        @Override
        public Optional<ConfiguracionGeneral> buscarPorId(UUID configuracionGeneralId) {
            return Optional.ofNullable(store.configuraciones.get(configuracionGeneralId));
        }

        @Override
        public Optional<ConfiguracionGeneral> obtenerVigente() {
            return vigente();
        }

        private Optional<ConfiguracionGeneral> vigente() {
            return store.configuraciones.values().stream()
                    .max(Comparator.comparing(ConfiguracionGeneral::getFechaUltimaActualizacion));
        }
    }

    private static class MedidaMemoryRepository implements MedidaAdministrativaUsuarioRepositoryPort {

        private final MvpInMemoryStore store;

        private MedidaMemoryRepository(MvpInMemoryStore store) {
            this.store = store;
        }

        @Override
        public MedidaAdministrativaUsuario guardar(MedidaAdministrativaUsuario medidaAdministrativaUsuario) {
            store.touch(medidaAdministrativaUsuario);
            store.medidas.put(medidaAdministrativaUsuario.getId(), medidaAdministrativaUsuario);
            return medidaAdministrativaUsuario;
        }

        @Override
        public Optional<MedidaAdministrativaUsuario> buscarPorId(UUID medidaAdministrativaUsuarioId) {
            return Optional.ofNullable(store.medidas.get(medidaAdministrativaUsuarioId));
        }

        @Override
        public List<MedidaAdministrativaUsuario> buscarPorUsuarioId(UUID usuarioId) {
            return store.medidas.values().stream()
                    .filter(medida -> Objects.equals(medida.getUsuarioId(), usuarioId))
                    .toList();
        }

        @Override
        public List<MedidaAdministrativaUsuario> buscarActivasPorUsuarioId(UUID usuarioId) {
            return buscarPorUsuarioId(usuarioId).stream()
                    .filter(MedidaAdministrativaUsuario::estaActiva)
                    .toList();
        }
    }

    private static class Sha256PasswordHasher implements PasswordHasherPort {

        @Override
        public String hashear(String passwordPlano) {
            return "sha256:" + sha256(passwordPlano == null ? "" : passwordPlano);
        }

        @Override
        public boolean coincide(String passwordPlano, String passwordHash) {
            return Objects.equals(hashear(passwordPlano), passwordHash);
        }
    }

    private static class SimpleTokenProvider implements TokenProviderPort {

        @Override
        public TokenResult generarAccessToken(UUID usuarioId, String emailAcceso) {
            LocalDateTime ahora = LocalDateTime.now();
            return new TokenResult(
                    "access-" + usuarioId + "-" + UUID.randomUUID(),
                    "Bearer",
                    ahora,
                    ahora.plusMinutes(30)
            );
        }

        @Override
        public TokenResult generarRefreshToken(UUID usuarioId, String emailAcceso) {
            LocalDateTime ahora = LocalDateTime.now();
            return new TokenResult(
                    "refresh-" + usuarioId + "-" + UUID.randomUUID(),
                    "Bearer",
                    ahora,
                    ahora.plusDays(7)
            );
        }

        @Override
        public String obtenerHashToken(String token) {
            return sha256(token == null ? "" : token);
        }
    }

    private static boolean equalsIgnoreCase(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return a.equalsIgnoreCase(b);
    }

    private static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo calcular SHA-256", e);
        }
    }
}
