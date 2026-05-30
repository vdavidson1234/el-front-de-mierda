package com.servify.administracion.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.servify.shared.domain.model.BaseEntity;

public class ConfiguracionGeneral extends BaseEntity {

    private Integer radioBusquedaInicialKm;
    private Integer radioBusquedaExpansionKm;
    private Integer tiempoEsperaExpansionMinutos;
    private Boolean validacionIdentidadRequerida;
    private BigDecimal precioBaseMinimoReferencia;
    private Boolean plataformaActiva;
    private LocalDateTime fechaUltimaActualizacion;

    protected ConfiguracionGeneral() {
    }

    public ConfiguracionGeneral(UUID id,
                                Integer radioBusquedaInicialKm,
                                Integer radioBusquedaExpansionKm,
                                Integer tiempoEsperaExpansionMinutos,
                                Boolean validacionIdentidadRequerida,
                                BigDecimal precioBaseMinimoReferencia,
                                Boolean plataformaActiva,
                                LocalDateTime fechaUltimaActualizacion) {
        super(id);
        this.radioBusquedaInicialKm = radioBusquedaInicialKm;
        this.radioBusquedaExpansionKm = radioBusquedaExpansionKm;
        this.tiempoEsperaExpansionMinutos = tiempoEsperaExpansionMinutos;
        this.validacionIdentidadRequerida = validacionIdentidadRequerida;
        this.precioBaseMinimoReferencia = precioBaseMinimoReferencia;
        this.plataformaActiva = plataformaActiva;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Integer getRadioBusquedaInicialKm() {
        return radioBusquedaInicialKm;
    }

    public Integer getRadioBusquedaExpansionKm() {
        return radioBusquedaExpansionKm;
    }

    public Integer getTiempoEsperaExpansionMinutos() {
        return tiempoEsperaExpansionMinutos;
    }

    public Boolean getValidacionIdentidadRequerida() {
        return validacionIdentidadRequerida;
    }

    public BigDecimal getPrecioBaseMinimoReferencia() {
        return precioBaseMinimoReferencia;
    }

    public Boolean getPlataformaActiva() {
        return plataformaActiva;
    }

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public boolean requiereValidacionIdentidad() {
        return this.validacionIdentidadRequerida != null && this.validacionIdentidadRequerida;
    }

    public boolean estaActiva() {
        return this.plataformaActiva != null && this.plataformaActiva;
    }

    public boolean configuracionBusquedaValida() {
        if (this.radioBusquedaInicialKm == null || this.radioBusquedaInicialKm <= 0) {
            return false;
        }
        if (this.radioBusquedaExpansionKm == null || this.radioBusquedaExpansionKm <= 0) {
            return false;
        }
        if (this.radioBusquedaExpansionKm <= this.radioBusquedaInicialKm) {
            return false;
        }
        if (this.tiempoEsperaExpansionMinutos == null || this.tiempoEsperaExpansionMinutos <= 0) {
            return false;
        }
        return true;
    }

    public void actualizarRadioBusquedaInicialKm(Integer radioBusquedaInicialKm) {
        if (radioBusquedaInicialKm == null) {
            throw new IllegalArgumentException("El radio de búsqueda inicial no puede ser nulo");
        }
        if (radioBusquedaInicialKm <= 0) {
            throw new IllegalArgumentException("El radio de búsqueda inicial debe ser un valor positivo");
        }
        this.radioBusquedaInicialKm = radioBusquedaInicialKm;
    }

    public void actualizarRadioBusquedaExpansionKm(Integer radioBusquedaExpansionKm) {
        if (radioBusquedaExpansionKm == null) {
            throw new IllegalArgumentException("El radio de expansión no puede ser nulo");
        }
        if (radioBusquedaExpansionKm <= 0) {
            throw new IllegalArgumentException("El radio de expansión debe ser un valor positivo");
        }
        if (this.radioBusquedaInicialKm != null && radioBusquedaExpansionKm <= this.radioBusquedaInicialKm) {
            throw new IllegalArgumentException("El radio de expansión debe ser mayor que el radio inicial");
        }
        this.radioBusquedaExpansionKm = radioBusquedaExpansionKm;
    }

    public void actualizarTiempoEsperaExpansionMinutos(Integer tiempoEsperaExpansionMinutos) {
        if (tiempoEsperaExpansionMinutos == null) {
            throw new IllegalArgumentException("El tiempo de espera para expansión no puede ser nulo");
        }
        if (tiempoEsperaExpansionMinutos <= 0) {
            throw new IllegalArgumentException("El tiempo de espera debe ser un valor positivo en minutos");
        }
        this.tiempoEsperaExpansionMinutos = tiempoEsperaExpansionMinutos;
    }

    public void actualizarValidacionIdentidadRequerida(Boolean validacionIdentidadRequerida) {
        if (validacionIdentidadRequerida == null) {
            throw new IllegalArgumentException("El requerimiento de validación de identidad no puede ser nulo");
        }
        this.validacionIdentidadRequerida = validacionIdentidadRequerida;
    }

    public void actualizarPrecioBaseMinimoReferencia(BigDecimal precioBaseMinimoReferencia) {
        if (precioBaseMinimoReferencia == null) {
            throw new IllegalArgumentException("El precio base mínimo de referencia no puede ser nulo");
        }
        if (precioBaseMinimoReferencia.signum() < 0) {
            throw new IllegalArgumentException("El precio base mínimo debe ser no negativo");
        }
        this.precioBaseMinimoReferencia = precioBaseMinimoReferencia;
    }

    public void actualizarPlataformaActiva(Boolean plataformaActiva) {
        if (plataformaActiva == null) {
            throw new IllegalArgumentException("El estado de la plataforma no puede ser nulo");
        }
        this.plataformaActiva = plataformaActiva;
    }

    public void activarPlataforma() {
        this.plataformaActiva = true;
    }

    public void desactivarPlataforma() {
        this.plataformaActiva = false;
    }

    public void actualizarFechaUltimaActualizacion(LocalDateTime fechaActualizacion) {
        if (fechaActualizacion == null) {
            throw new IllegalArgumentException("La fecha de actualización no puede ser nula");
        }
        this.fechaUltimaActualizacion = fechaActualizacion;
    }
}