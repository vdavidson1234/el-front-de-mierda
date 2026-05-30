package com.servify.usuarios.domain.service;

import com.servify.usuarios.domain.model.Usuario;

public class PoliticaHabilitacionPublicacion {

    /**
     * Determina si un usuario puede publicar servicios en la plataforma.
     */
    public boolean puedePublicar(Usuario usuario) {
        return usuario != null && usuario.puedePublicarServicios();
    }
}
