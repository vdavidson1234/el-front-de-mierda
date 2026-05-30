package com.servify.publicaciones.application.service;

import com.servify.publicaciones.application.dto.CambiarEstadoCategoriaServicioCommand;
import com.servify.publicaciones.application.dto.CategoriaServicioResult;
import com.servify.publicaciones.application.port.in.CambiarEstadoCategoriaServicioUseCase;
import com.servify.publicaciones.application.port.out.CategoriaServicioRepositoryPort;
import com.servify.publicaciones.domain.enumtype.EstadoCategoria;
import com.servify.publicaciones.domain.model.CategoriaServicio;

import java.util.UUID;

/**
 * Servicio de aplicacion que ejecuta el cambio de estado de categoria.
 */
public class CambiarEstadoCategoriaServicioService implements CambiarEstadoCategoriaServicioUseCase {

    private final CategoriaServicioRepositoryPort categoriaServicioRepositoryPort;

    public CambiarEstadoCategoriaServicioService(CategoriaServicioRepositoryPort categoriaServicioRepositoryPort) {
        this.categoriaServicioRepositoryPort = categoriaServicioRepositoryPort;
    }

    @Override
    public CategoriaServicioResult cambiarEstado(CambiarEstadoCategoriaServicioCommand command) {
        // Valida command, recupera categoría, aplica transición, persiste y retorna resultado
        if (command == null || command.getCategoriaServicioId() == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo.");
        }
        if (command.getEstadoDestino() == null) {
            throw new IllegalArgumentException("El estado destino no puede ser nulo.");
        }
        CategoriaServicio categoriaServicio = obtenerCategoriaExistente(command.getCategoriaServicioId());
        aplicarEstadoDestino(categoriaServicio, command.getEstadoDestino());
        CategoriaServicio categoriaGuardada = categoriaServicioRepositoryPort.guardar(categoriaServicio);
        return construirResultado(categoriaGuardada);
    }

    // Busca la categoría por ID y lanza excepción si no existe
    protected CategoriaServicio obtenerCategoriaExistente(UUID categoriaServicioId) {
        return categoriaServicioRepositoryPort.buscarPorId(categoriaServicioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la categoría con id: " + categoriaServicioId));
    }

    // Despacha la transición de estado según el destino indicado
    protected void aplicarEstadoDestino(CategoriaServicio categoriaServicio,
                                        EstadoCategoria estadoDestino) {
        switch (estadoDestino) {
            case ACTIVA -> categoriaServicio.activar();
            case INACTIVA -> categoriaServicio.desactivar();
            default -> throw new IllegalArgumentException("Estado destino no soportado: " + estadoDestino);
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
}
