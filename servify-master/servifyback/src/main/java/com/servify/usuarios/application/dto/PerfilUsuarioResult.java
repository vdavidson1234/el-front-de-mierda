package com.servify.usuarios.application.dto;

import java.util.UUID;

public class PerfilUsuarioResult {

    private UUID id;
    private UUID usuarioId;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String fotoPerfilUrl;
    private UbicacionResult ubicacion;
    private String descripcionPersonal;
    private Boolean perfilCompleto;

    private PerfilUsuarioResult() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public UbicacionResult getUbicacion() {
        return ubicacion;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }

    public Boolean getPerfilCompleto() {
        return perfilCompleto;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final PerfilUsuarioResult instance;

        public Builder() {
            this.instance = new PerfilUsuarioResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder usuarioId(UUID usuarioId) {
            instance.usuarioId = usuarioId;
            return this;
        }

        public Builder nombre(String nombre) {
            instance.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            instance.apellido = apellido;
            return this;
        }

        public Builder edad(Integer edad) {
            instance.edad = edad;
            return this;
        }

        public Builder fotoPerfilUrl(String fotoPerfilUrl) {
            instance.fotoPerfilUrl = fotoPerfilUrl;
            return this;
        }

        public Builder ubicacion(UbicacionResult ubicacion) {
            instance.ubicacion = ubicacion;
            return this;
        }

        public Builder descripcionPersonal(String descripcionPersonal) {
            instance.descripcionPersonal = descripcionPersonal;
            return this;
        }

        public Builder perfilCompleto(Boolean perfilCompleto) {
            instance.perfilCompleto = perfilCompleto;
            return this;
        }

        public PerfilUsuarioResult build() {
            return instance;
        }
    }
}