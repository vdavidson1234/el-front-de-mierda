package com.servify.autenticacion.application.dto;

public class IniciarSesionCommand {

    private String emailAcceso;
    private String passwordPlano;

    public IniciarSesionCommand() {
    }

    public IniciarSesionCommand(String emailAcceso,
                                String passwordPlano) {
        this.emailAcceso = emailAcceso;
        this.passwordPlano = passwordPlano;
    }

    public String getEmailAcceso() {
        return emailAcceso;
    }

    public String getPasswordPlano() {
        return passwordPlano;
    }
}
