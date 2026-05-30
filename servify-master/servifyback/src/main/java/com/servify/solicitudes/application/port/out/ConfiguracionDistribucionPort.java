package com.servify.solicitudes.application.port.out;

import java.math.BigDecimal;

public interface ConfiguracionDistribucionPort {

    Integer obtenerRadioBusquedaInicialKm();

    Integer obtenerRadioBusquedaExpansionKm();

    Integer obtenerTiempoEsperaExpansionMinutos();

    BigDecimal obtenerPrecioBaseMinimoReferencia();
}
