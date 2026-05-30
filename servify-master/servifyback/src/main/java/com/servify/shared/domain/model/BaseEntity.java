package com.servify.shared.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//Base abstracta para entidades del dominio.


public abstract class BaseEntity {

    private UUID id ;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaUltimaModificacion;


    //constructores simples
    protected BaseEntity() {
    }

    protected BaseEntity(UUID id) {
        this.id = id;
    }

    //getters y setters
    public UUID getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void marcarCreacion(LocalDateTime fechaHora) {
        this.fechaCreacion = fechaHora;
        this.fechaUltimaModificacion = fechaHora;
    }

    public void marcarModificacion(LocalDateTime fechaHora) {
        this.fechaUltimaModificacion = fechaHora;
    }

    //validacion simple
    public boolean mismoIdQue(BaseEntity otraEntidad) {
        if (otraEntidad == null) {
            return false;
        }
        return Objects.equals(this.id, otraEntidad.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity other)) {
            return false;
        }
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
