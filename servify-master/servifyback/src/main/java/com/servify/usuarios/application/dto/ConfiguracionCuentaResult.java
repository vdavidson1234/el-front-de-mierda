package com.servify.usuarios.application.dto;

//Es el DTO de salida para la pantalla de configuración de cuenta.
//junta en un solo resultado informaicno de perfiil y de usuario.

public class ConfiguracionCuentaResult {

    private UsuarioResult usuario;
    private PerfilUsuarioResult perfil;

    public ConfiguracionCuentaResult() {
    }

    public ConfiguracionCuentaResult(UsuarioResult usuario, PerfilUsuarioResult perfil) {
        this.usuario = usuario;
        this.perfil = perfil;
    }

    public UsuarioResult getUsuario() {
        return usuario;
    }

    public PerfilUsuarioResult getPerfil() {
        return perfil;
    }
}