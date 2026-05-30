package com.servify.usuarios.domain.service;

import com.servify.usuarios.domain.model.PerfilUsuario;

public class PoliticaPerfilCompleto {

    /**
     * Determina si un perfil cumple con los datos obligatorios definidos por Servify.
     */
    public boolean evaluar(PerfilUsuario perfil) {
        return perfil != null && perfil.estaCompleto();
    }
}
