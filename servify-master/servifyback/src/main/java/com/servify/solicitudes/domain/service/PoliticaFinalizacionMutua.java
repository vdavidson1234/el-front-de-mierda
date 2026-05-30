package com.servify.solicitudes.domain.service;

import com.servify.solicitudes.domain.model.ConfirmacionFinalizacion;
import com.servify.solicitudes.domain.model.SolicitudServicio;

import java.util.List;

//no pertenece solo a SolicitudServicio
//tampoco pertenece solo a ConfirmacionFinalizacion
//y la van a usar después los casos de uso de cierre/finalización

public class PoliticaFinalizacionMutua {

    public boolean puedeFinalizarse(SolicitudServicio solicitud,
                                    List<ConfirmacionFinalizacion> confirmaciones) {
        if (solicitud == null || confirmaciones == null) {
            return false;
        }
        
        return estanAmbasConfirmacionesPresentes(confirmaciones);
    }

    public boolean existeConfirmacionDelSolicitante(List<ConfirmacionFinalizacion> confirmaciones) {
        if (confirmaciones == null || confirmaciones.isEmpty()) {
            return false;
        }
        
        return confirmaciones.stream()
                .anyMatch(c -> c != null && c.esDelSolicitante() && c.estaConfirmada());
    }

    public boolean existeConfirmacionDelPrestador(List<ConfirmacionFinalizacion> confirmaciones) {
        if (confirmaciones == null || confirmaciones.isEmpty()) {
            return false;
        }
        
        return confirmaciones.stream()
                .anyMatch(c -> c != null && c.esDelPrestador() && c.estaConfirmada());
    }

    public boolean estanAmbasConfirmacionesPresentes(List<ConfirmacionFinalizacion> confirmaciones) {
        return existeConfirmacionDelSolicitante(confirmaciones) && 
               existeConfirmacionDelPrestador(confirmaciones);
    }
}