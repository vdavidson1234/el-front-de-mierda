package com.servify.publicaciones.application.service;

import com.servify.publicaciones.application.dto.CategoriaServicioResult;
import com.servify.publicaciones.application.port.in.ListarCategoriasActivasUseCase;
import com.servify.publicaciones.application.port.out.CategoriaServicioRepositoryPort;
import com.servify.publicaciones.domain.model.CategoriaServicio;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicacion que lista categorias de servicio activas.
 */
public class ListarCategoriasActivasService implements ListarCategoriasActivasUseCase {

    private final CategoriaServicioRepositoryPort categoriaServicioRepositoryPort;

    public ListarCategoriasActivasService(CategoriaServicioRepositoryPort categoriaServicioRepositoryPort) {
        this.categoriaServicioRepositoryPort = categoriaServicioRepositoryPort;
    }

    // Delega en el repositorio y mapea cada categoría al DTO de salida
    @Override
    public List<CategoriaServicioResult> listarActivas() {
        return categoriaServicioRepositoryPort.listarActivas()
                .stream()
                .map(this::construirResultado)
                .collect(Collectors.toList());
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
}
