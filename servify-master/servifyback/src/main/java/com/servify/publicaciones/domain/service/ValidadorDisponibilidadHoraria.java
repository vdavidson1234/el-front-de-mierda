package com.servify.publicaciones.domain.service;

import com.servify.shared.domain.valueobject.DisponibilidadHoraria;

import java.util.List;

public class ValidadorDisponibilidadHoraria {

    // Valida que la lista no sea nula ni vacía, que cada franja sea válida y que no haya solapamientos
    public boolean sonValidas(List<DisponibilidadHoraria> disponibilidades) {
        if (disponibilidades == null || disponibilidades.isEmpty()) {
            return false;
        }
        for (DisponibilidadHoraria d : disponibilidades) {
            if (!d.esRangoHorarioValido()) {
                return false;
            }
        }
        return !haySuperposiciones(disponibilidades);
    }

    // Detecta si alguna franja horaria se superpone con otra del mismo día
    public boolean haySuperposiciones(List<DisponibilidadHoraria> disponibilidades) {
        if (disponibilidades == null || disponibilidades.size() < 2) {
            return false;
        }
        for (int i = 0; i < disponibilidades.size(); i++) {
            for (int j = i + 1; j < disponibilidades.size(); j++) {
                if (disponibilidades.get(i).seSuperponeCon(disponibilidades.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
