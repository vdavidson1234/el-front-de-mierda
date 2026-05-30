package com.servify.solicitudes.domain.service;

import com.servify.solicitudes.domain.model.DistribucionSolicitud;

import java.time.LocalDateTime;
import java.util.List;

public class PoliticaExpiracionDistribucion {

    public boolean debeExpirar(DistribucionSolicitud distribucion,
                               LocalDateTime fechaActual) {
        if (distribucion == null || fechaActual == null) {
            return false;
        }
        
        if (!distribucion.estaEnviada()) {
            return false;
        }
        
        LocalDateTime fechaExpiracion = distribucion.getFechaExpiracion();
        if (fechaExpiracion == null) {
            return false;
        }
        
        return fechaActual.isAfter(fechaExpiracion) || fechaActual.equals(fechaExpiracion);
    }

    public boolean existenDistribucionesExpirables(List<DistribucionSolicitud> distribuciones,
                                                   LocalDateTime fechaActual) {
        if (distribuciones == null || distribuciones.isEmpty() || fechaActual == null) {
            return false;
        }
        
        return distribuciones.stream()
                .anyMatch(d -> d != null && debeExpirar(d, fechaActual));
    }

    public boolean puedeSeguirEsperandoRespuesta(DistribucionSolicitud distribucion,
                                                 LocalDateTime fechaActual) {
        if (distribucion == null || fechaActual == null) {
            return false;
        }
        
        return distribucion.estaEnviada() && !debeExpirar(distribucion, fechaActual);
    }
}