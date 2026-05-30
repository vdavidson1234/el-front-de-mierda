package com.servify.autenticacion.application.port.out;

public interface PasswordHasherPort {

    String hashear(String passwordPlano);

    boolean coincide(String passwordPlano, String passwordHash);
}
