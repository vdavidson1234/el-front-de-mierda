package com.servify.usuarios.application.service;

import com.servify.shared.domain.exception.BusinessRuleException;
import com.servify.shared.domain.exception.ValidationException;
import com.servify.usuarios.application.dto.CrearUsuarioCommand;
import com.servify.usuarios.application.dto.UsuarioResult;
import com.servify.usuarios.application.port.in.CrearUsuarioUseCase;
import com.servify.usuarios.application.port.out.UsuarioRepositoryPort;
import com.servify.usuarios.domain.enumtype.EstadoUsuario;
import com.servify.usuarios.domain.enumtype.EstadoValidacionIdentidad;
import com.servify.usuarios.domain.model.Usuario;
import com.servify.usuarios.domain.valueobject.Contacto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CrearUsuarioService implements CrearUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public CrearUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    /**
     * Crea una cuenta nueva.
     *
     * Flujo:
     * - valida que el command exista
     * - construye el value object Contacto
     * - verifica que el email no exista previamente
     * - crea la entidad Usuario con estado inicial valido
     * - persiste el usuario
     * - devuelve el resultado mapeado a UsuarioResult
     */
    @Override
    public UsuarioResult crear(CrearUsuarioCommand command) {
        if (command == null) {
            throw new ValidationException("El command de creacion de usuario es obligatorio");
        }

        Contacto contacto = construirContacto(command);

        if (usuarioRepositoryPort.existePorEmail(contacto.getEmail())) {
            throw new BusinessRuleException("Ya existe un usuario registrado con el email informado");
        }

        Usuario usuario = construirUsuario(command);
        Usuario usuarioPersistido = usuarioRepositoryPort.guardar(usuario);

        return construirResultado(usuarioPersistido);
    }

    protected Usuario construirUsuario(CrearUsuarioCommand command) {
        // Construye la entidad con id nuevo, contacto valido y estado inicial.
        if (command == null) {
            throw new ValidationException("El command de creacion de usuario es obligatorio");
        }

        return new Usuario(
                generarIdUsuario(),
                construirContacto(command),
                command.getRol(),
                obtenerEstadoInicialUsuario(),
                obtenerEstadoInicialValidacionIdentidad(),
                null,
                obtenerFechaRegistroActual()
        );
    }

    protected Contacto construirContacto(CrearUsuarioCommand command) {
        // Construye el value object Contacto a partir del email y telefono del command.
        if (command == null) {
            throw new ValidationException("El command de creacion de usuario es obligatorio");
        }

        String email = normalizarTextoObligatorio(command.getEmail(), "El email es obligatorio");
        String telefono = normalizarTextoOpcional(command.getTelefono());

        Contacto contacto = new Contacto(email, telefono);

        if (!contacto.emailValido()) {
            throw new ValidationException("El email no tiene un formato valido");
        }

        return contacto;
    }

    protected UsuarioResult construirResultado(Usuario usuario) {
        // Mapea la entidad persistida al DTO de salida de la aplicacion.
        if (usuario == null) {
            throw new ValidationException("El usuario es obligatorio");
        }

        Contacto contacto = usuario.getContacto();

        return new UsuarioResult(
                usuario.getId(),
                contacto != null ? contacto.getEmail() : null,
                contacto != null ? contacto.getTelefono() : null,
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getEstadoValidacionIdentidad(),
                usuario.getFechaRegistro()
        );
    }

    protected EstadoUsuario obtenerEstadoInicialUsuario() {
        // Define el estado con el que se registra una cuenta nueva.
        return EstadoUsuario.ACTIVO;
    }

    protected EstadoValidacionIdentidad obtenerEstadoInicialValidacionIdentidad() {
        // Define el estado inicial de validacion de identidad.
        return EstadoValidacionIdentidad.NO_REQUERIDA;
    }

    protected UUID generarIdUsuario() {
        // Genera un identificador simple basado en UUID aleatorio.
        return UUID.randomUUID();
    }

    protected LocalDateTime obtenerFechaRegistroActual() {
        // Centraliza la fecha y hora de registro.
        return LocalDateTime.now();
    }

    private String normalizarTextoOpcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }

    private String normalizarTextoObligatorio(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }

        return valor.trim();
    }
}
