package com.servify.administracion.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ActualizarConfiguracionGeneralCommand {

    private UUID configuracionGeneralId;
    private UUID administradorId;
    private Integer radioBusquedaInicialKm;
    private Integer radioBusquedaExpansionKm;
    private Integer tiempoEsperaExpansionMinutos;
    private Boolean validacionIdentidadRequerida;
    private BigDecimal precioBaseMinimoReferencia;
    private Boolean plataformaActiva;

    public ActualizarConfiguracionGeneralCommand() {
    }

    public ActualizarConfiguracionGeneralCommand(UUID configuracionGeneralId,
                                                 UUID administradorId,
                                                 Integer radioBusquedaInicialKm,
                                                 Integer radioBusquedaExpansionKm,
                                                 Integer tiempoEsperaExpansionMinutos,
                                                 Boolean validacionIdentidadRequerida,
                                                 BigDecimal precioBaseMinimoReferencia,
                                                 Boolean plataformaActiva) {
        this.configuracionGeneralId = configuracionGeneralId;
        this.administradorId = administradorId;
        this.radioBusquedaInicialKm = radioBusquedaInicialKm;
        this.radioBusquedaExpansionKm = radioBusquedaExpansionKm;
        this.tiempoEsperaExpansionMinutos = tiempoEsperaExpansionMinutos;
        this.validacionIdentidadRequerida = validacionIdentidadRequerida;
        this.precioBaseMinimoReferencia = precioBaseMinimoReferencia;
        this.plataformaActiva = plataformaActiva;
    }

    public UUID getConfiguracionGeneralId() {
        return configuracionGeneralId;
    }

    public UUID getAdministradorId() {
        return administradorId;
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
}
