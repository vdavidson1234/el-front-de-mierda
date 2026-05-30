package com.servify.solicitudes.application.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DisponibilidadHorariaResult {

    private DayOfWeek diaSemana;
    private LocalTime horaDesde;
    private LocalTime horaHasta;

    public DisponibilidadHorariaResult() {
    }

    public DisponibilidadHorariaResult(DayOfWeek diaSemana,
                                       LocalTime horaDesde,
                                       LocalTime horaHasta) {
        this.diaSemana = diaSemana;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraDesde() {
        return horaDesde;
    }

    public LocalTime getHoraHasta() {
        return horaHasta;
    }
}