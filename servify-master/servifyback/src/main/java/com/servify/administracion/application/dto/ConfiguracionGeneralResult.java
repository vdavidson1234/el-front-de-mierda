package com.servify.administracion.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ConfiguracionGeneralResult {

    private UUID id;
    private Integer radioBusquedaInicialKm;
    private Integer radioBusquedaExpansionKm;
    private Integer tiempoEsperaExpansionMinutos;
    private Boolean validacionIdentidadRequerida;
    private BigDecimal precioBaseMinimoReferencia;
    private Boolean plataformaActiva;
    private LocalDateTime fechaUltimaActualizacion;

    private ConfiguracionGeneralResult() {
    }

    public UUID getId() {
        return id;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ConfiguracionGeneralResult instance;

        public Builder() {
            this.instance = new ConfiguracionGeneralResult();
        }

        public Builder id(UUID id) {
            instance.id = id;
            return this;
        }

        public Builder radioBusquedaInicialKm(Integer radioBusquedaInicialKm) {
            instance.radioBusquedaInicialKm = radioBusquedaInicialKm;
            return this;
        }

        public Builder radioBusquedaExpansionKm(Integer radioBusquedaExpansionKm) {
            instance.radioBusquedaExpansionKm = radioBusquedaExpansionKm;
            return this;
        }

        public Builder tiempoEsperaExpansionMinutos(Integer tiempoEsperaExpansionMinutos) {
            instance.tiempoEsperaExpansionMinutos = tiempoEsperaExpansionMinutos;
            return this;
        }

        public Builder validacionIdentidadRequerida(Boolean validacionIdentidadRequerida) {
            instance.validacionIdentidadRequerida = validacionIdentidadRequerida;
            return this;
        }

        public Builder precioBaseMinimoReferencia(BigDecimal precioBaseMinimoReferencia) {
            instance.precioBaseMinimoReferencia = precioBaseMinimoReferencia;
            return this;
        }

        public Builder plataformaActiva(Boolean plataformaActiva) {
            instance.plataformaActiva = plataformaActiva;
            return this;
        }

        public Builder fechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) {
            instance.fechaUltimaActualizacion = fechaUltimaActualizacion;
            return this;
        }

        public ConfiguracionGeneralResult build() {
            return instance;
        }
    }
}
