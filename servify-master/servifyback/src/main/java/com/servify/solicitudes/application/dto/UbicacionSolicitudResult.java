package com.servify.solicitudes.application.dto;

public class UbicacionSolicitudResult {

    private String pais;
    private String provincia;
    private String ciudad;
    private String localidad;
    private String calle;
    private String altura;
    private String referencia;
    private Double latitud;
    private Double longitud;

    public UbicacionSolicitudResult() {
    }

    public UbicacionSolicitudResult(String pais,
                                    String provincia,
                                    String ciudad,
                                    String localidad,
                                    String calle,
                                    String altura,
                                    String referencia,
                                    Double latitud,
                                    Double longitud) {
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.localidad = localidad;
        this.calle = calle;
        this.altura = altura;
        this.referencia = referencia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getPais() {
        return pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getCalle() {
        return calle;
    }

    public String getAltura() {
        return altura;
    }

    public String getReferencia() {
        return referencia;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }
}