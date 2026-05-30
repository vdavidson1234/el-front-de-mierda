package com.servify.publicaciones.application.service;

import com.servify.publicaciones.application.dto.CategoriaServicioResult;
import com.servify.publicaciones.application.dto.CrearCategoriaServicioCommand;
import com.servify.publicaciones.application.port.in.CrearCategoriaServicioUseCase;
import com.servify.publicaciones.application.port.out.CategoriaServicioRepositoryPort;
import com.servify.publicaciones.domain.enumtype.EstadoCategoria;
import com.servify.publicaciones.domain.model.CategoriaServicio;

import java.util.UUID;

/**
 * Servicio de aplicacion que ejecuta el caso de uso de crear categoria.
 */
public class CrearCategoriaServicioService implements CrearCategoriaServicioUseCase {

    private final CategoriaServicioRepositoryPort categoriaServicioRepositoryPort;

    public CrearCategoriaServicioService(CategoriaServicioRepositoryPort categoriaServicioRepositoryPort) {
        this.categoriaServicioRepositoryPort = categoriaServicioRepositoryPort;
    }

    @Override
    public CategoriaServicioResult crear(CrearCategoriaServicioCommand command) {
        // Valida command, unicidad de nombre, construye, persiste y retorna resultado
        if (command == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo.");
        }
        if (command.getNombre() == null || command.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo ni vacío.");
        }
        if (categoriaServicioRepositoryPort.existePorNombre(command.getNombre())) {
            throw new IllegalStateException("Ya existe una categoría con el nombre: " + command.getNombre());
        }
        CategoriaServicio categoriaServicio = construirCategoria(command);
        CategoriaServicio categoriaGuardada = categoriaServicioRepositoryPort.guardar(categoriaServicio);
        return construirResultado(categoriaGuardada);
    }

    // Construye la entidad con estado inicial INACTIVA hasta que sea habilitada explícitamente
    protected CategoriaServicio construirCategoria(CrearCategoriaServicioCommand command) {
        return new CategoriaServicio(
                generarIdCategoria(),
                command.getNombre().trim(),
                command.getDescripcion() != null ? command.getDescripcion().trim() : null,
                EstadoCategoria.INACTIVA
        );
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

    // Genera un identificador único para la nueva categoría
    protected UUID generarIdCategoria() {
        return UUID.randomUUID();
    }
}
