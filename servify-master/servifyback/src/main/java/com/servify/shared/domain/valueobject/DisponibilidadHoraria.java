package com.servify.shared.domain.valueobject;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class DisponibilidadHoraria {

    private DayOfWeek diaSemana;
    private LocalTime horaDesde;
    private LocalTime horaHasta;

    protected DisponibilidadHoraria() {
    }

    public DisponibilidadHoraria(DayOfWeek diaSemana,
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

    public boolean esRangoHorarioValido() {
        if (this.diaSemana == null) {
            return false;
        }
        if (this.horaDesde == null || this.horaHasta == null) {
            return false;
        }
        return this.horaDesde.isBefore(this.horaHasta);
    }

    public boolean incluyeHora(LocalTime hora) {
        if (hora == null) {
            return false;
        }
        if (!esRangoHorarioValido()) {
            return false;
        }
        // Inclusivo en horaDesde, exclusivo en horaHasta
        return !hora.isBefore(this.horaDesde) && hora.isBefore(this.horaHasta);
    }

    public boolean seSuperponeCon(DisponibilidadHoraria otraDisponibilidad) {
        if (otraDisponibilidad == null) {
            return false;
        }
        if (this.diaSemana == null || otraDisponibilidad.diaSemana == null) {
            return false;
        }
        if (!this.diaSemana.equals(otraDisponibilidad.diaSemana)) {
            return false;
        }
        if (!this.esRangoHorarioValido() || !otraDisponibilidad.esRangoHorarioValido()) {
            return false;
        }
        LocalTime inicioMax = this.horaDesde.isAfter(otraDisponibilidad.horaDesde) ? this.horaDesde : otraDisponibilidad.horaDesde;
        LocalTime finMin = this.horaHasta.isBefore(otraDisponibilidad.horaHasta) ? this.horaHasta : otraDisponibilidad.horaHasta;
        return inicioMax.isBefore(finMin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisponibilidadHoraria that)) {
            return false;
        }
        return diaSemana == that.diaSemana
                && Objects.equals(horaDesde, that.horaDesde)
                && Objects.equals(horaHasta, that.horaHasta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaSemana, horaDesde, horaHasta);
    }
}