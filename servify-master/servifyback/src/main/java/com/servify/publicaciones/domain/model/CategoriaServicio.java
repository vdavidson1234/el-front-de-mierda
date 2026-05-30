package com.servify.publicaciones.domain.model;

import com.servify.publicaciones.domain.enumtype.EstadoCategoria;
import com.servify.shared.domain.model.BaseEntity;

import java.util.UUID;

public class CategoriaServicio extends BaseEntity {

    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;

    protected CategoriaServicio() {
    }

    public CategoriaServicio(UUID id,
                             String nombre,
                             String descripcion,
                             EstadoCategoria estado) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoCategoria getEstado() {
        return estado;
    }

    // Devuelve true si la categoría está habilitada
    public boolean estaActiva() {
        return EstadoCategoria.ACTIVA.equals(this.estado);
    }

    // Actualiza el nombre validando que no sea nulo ni vacío
    public void actualizarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    // Actualiza la descripción validando longitud y contenido
    public void actualizarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula ni vacía.");
        }
        if (descripcion.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede superar los 500 caracteres.");
        }
        this.descripcion = descripcion.trim();
    }

    // Cambia el estado a ACTIVA si no lo estaba ya
    public void activar() {
        if (EstadoCategoria.ACTIVA.equals(this.estado)) {
            throw new IllegalStateException("La categoría ya se encuentra activa.");
        }
        this.estado = EstadoCategoria.ACTIVA;
    }

    // Cambia el estado a INACTIVA para impedir su uso en nuevas publicaciones
    public void desactivar() {
        if (EstadoCategoria.INACTIVA.equals(this.estado)) {
            throw new IllegalStateException("La categoría ya se encuentra inactiva.");
        }
        this.estado = EstadoCategoria.INACTIVA;
    }
}
