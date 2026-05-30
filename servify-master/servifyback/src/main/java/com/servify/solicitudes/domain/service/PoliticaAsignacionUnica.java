package com.servify.solicitudes.domain.service;

import com.servify.solicitudes.domain.model.AsignacionServicio;
import com.servify.solicitudes.domain.model.SolicitudServicio;

import java.util.List;

//expresa una regla central del negocio
//no pertenece a una sola entidad
//puede ser usada por casos de uso y por el motor de distribución/asignación

public class PoliticaAsignacionUnica {

    public boolean puedeAsignarse(SolicitudServicio solicitud,
                                  List<AsignacionServicio> asignacionesExistentes) {
        if (solicitud == null || asignacionesExistentes == null) {
            return false;
        }
        
        if (!existeAsignacionVigente(asignacionesExistentes)) {
            return true;
        }
        
        return false;
    }

    public boolean existeAsignacionVigente(List<AsignacionServicio> asignacionesExistentes) {
        if (asignacionesExistentes == null || asignacionesExistentes.isEmpty()) {
            return false;
        }
        
        return asignacionesExistentes.stream()
                .anyMatch(a -> a != null && (a.getEstado() != null && 
                        (a.getEstado().toString().equals("ACTIVA") || 
                         a.getEstado().toString().equals("FINALIZADA"))));
    }

    public boolean requiereCerrarDistribucionesRestantes() {
        return true;
    }
}