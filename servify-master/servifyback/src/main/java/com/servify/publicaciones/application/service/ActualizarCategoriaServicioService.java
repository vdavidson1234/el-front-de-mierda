package com.servify.publicaciones.application.service;

import com.servify.publicaciones.application.dto.ActualizarCategoriaServicioCommand;
import com.servify.publicaciones.application.dto.CategoriaServicioResult;
import com.servify.publicaciones.application.port.in.ActualizarCategoriaServicioUseCase;
import com.servify.publicaciones.application.port.out.CategoriaServicioRepositoryPort;
import com.servify.publicaciones.domain.model.CategoriaServicio;

import java.util.UUID;

/**
 * Servicio de aplicacion que ejecuta el caso de uso de actualizar categoria.
 */
public class ActualizarCategoriaServicioService implements ActualizarCategoriaServicioUseCase {

    private final CategoriaServicioRepositoryPort categoriaServicioRepositoryPort;

    public ActualizarCategoriaServicioService(CategoriaServicioRepositoryPort categoriaServicioRepositoryPort) {
        this.categoriaServicioRepositoryPort = categoriaServicioRepositoryPort;
    }

    @Override
    public CategoriaServicioResult actualizar(ActualizarCategoriaServicioCommand command) {
        // Valida command, aplica cambios, persiste y retorna resultado
        if (command == null || command.getCategoriaServicioId() == null) {
            throw new IllegalArgumentException("El comando de actualización no puede ser nulo.");
        }
        CategoriaServicio categoriaServicio = obtenerCategoriaExistente(command.getCategoriaServicioId());
        validarUnicidadNombre(categoriaServicio, command);
        aplicarActualizaciones(categoriaServicio, command);
        CategoriaServicio categoriaGuardada = categoriaServicioRepositoryPort.guardar(categoriaServicio);
        return construirResultado(categoriaGuardada);
    }

    // Busca la categoría por ID y lanza excepción si no existe
    protected CategoriaServicio obtenerCategoriaExistente(UUID categoriaServicioId) {
        return categoriaServicioRepositoryPort.buscarPorId(categoriaServicioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la categoría con id: " + categoriaServicioId));
    }

    // Aplica cambios de nombre y descripción si vienen informados
    protected void aplicarActualizaciones(CategoriaServicio categoriaServicio,
                                          ActualizarCategoriaServicioCommand command) {
        if (command.getNombre() != null && !command.getNombre().isBlank()) {
            categoriaServicio.actualizarNombre(command.getNombre());
        }
        if (command.getDescripcion() != null && !command.getDescripcion().isBlank()) {
            categoriaServicio.actualizarDescripcion(command.getDescripcion());
        }
    }

    // Mapea la entidad de dominio al DTO de salida
    protected CategoriaServicioResult construirResultado(CategoriaServicio categoriaServicio) {
        return CategoriaServicioResult.builder()
                .id(categoriaServicio.getId())
                .nombre(categoriaServicio.getNombre())
                .descripcion(categoriaServicio.getDescripcion())
                .estado(categoriaServicio.getEstado())
                .fechaCreacion(categoriaServicio.getFechaCreacion())
                .fechaUltimaModificacion(categoriaServicio.getFechaUltimaModificacion())
                .build();
    }

    // Valida que el nuevo nombre no esté en uso por otra categoría
    private void validarUnicidadNombre(CategoriaServicio categoriaServicio,
                                       ActualizarCategoriaServicioCommand command) {
        if (command.getNombre() == null || command.getNombre().isBlank()) {
            return;
        }
        if (command.getNombre().equalsIgnoreCase(categoriaServicio.getNombre())) {
            return;
        }
        if (categoriaServicioRepositoryPort.existePorNombre(command.getNombre())) {
            throw new IllegalStateException(
                    "Ya existe una categoría con el nombre: " + command.getNombre());
        }
    }
}
