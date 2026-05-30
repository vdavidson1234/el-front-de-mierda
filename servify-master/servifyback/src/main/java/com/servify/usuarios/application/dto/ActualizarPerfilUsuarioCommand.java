package com.servify.usuarios.application.dto;

import com.servify.shared.domain.valueobject.Ubicacion;

import java.util.UUID;

public class ActualizarPerfilUsuarioCommand {

    private UUID usuarioId;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String fotoPerfilUrl;
    private Ubicacion ubicacion;
    private String descripcionPersonal;

    public ActualizarPerfilUsuarioCommand() {
    }

    public ActualizarPerfilUsuarioCommand(UUID usuarioId,
                                          String nombre,
                                          String apellido,
                                          Integer edad,
                                          String fotoPerfilUrl,
                                          Ubicacion ubicacion,
                                          String descripcionPersonal) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.ubicacion = ubicacion;
        this.descripcionPersonal = descripcionPersonal;
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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }
}