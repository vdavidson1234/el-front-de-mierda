package com.servify.shared.domain.valueobject;

import java.util.Objects;

/**
 * Clase que representa una ubicación geográfica.
 * Contiene información sobre la dirección (país, provincia, ciudad, etc.)
 * y coordenadas geográficas (latitud y longitud).
 * Esta es una clase Value Object que encapsula toda la información de una ubicación.
 */
public class Ubicacion {

    // Atributos que conforman una ubicación completa
    private String pais;
    private String provincia;
    private String ciudad;
    private String localidad;
    private String calle;
    private String altura;
    private String referencia;
    private Double latitud;
    private Double longitud;

    /**
     * Constructor protegido sin argumentos.
     * Utilizado principalmente por frameworks de persistencia como JPA/Hibernate.
     */
    protected Ubicacion() {
    }

    /**
     * Constructor público que inicializa todos los atributos de la ubicación.
     * Crea una nueva instancia de Ubicacion con la información geográfica completa.

     * @param pais El país de la ubicación
     * @param provincia La provincia del país
     * @param ciudad La ciudad principal
     * @param localidad La localidad específica (barrio, distrito, etc.)
     * @param calle El nombre de la calle
     * @param altura El número de altura en la calle
     * @param referencia Información adicional para ubicar con mayor precisión
     * @param latitud La coordenada de latitud (-90 a 90)
     * @param longitud La coordenada de longitud (-180 a 180)
     */
    public Ubicacion(String pais,
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

    /**
     * Obtiene el país de la ubicación.
     * @return El nombre del país
     */
    public String getPais() {
        return pais;
    }

    /**
     * Obtiene la provincia de la ubicación.
     * @return El nombre de la provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Obtiene la ciudad de la ubicación.
     * @return El nombre de la ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Obtiene la localidad (barrio, distrito, etc.) de la ubicación.
     * @return El nombre de la localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Obtiene el nombre de la calle.
     * @return El nombre de la calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Obtiene el número de altura en la calle.
     * @return El número de altura
     */
    public String getAltura() {
        return altura;
    }

    /**
     * Obtiene la referencia adicional para ubicar con mayor precisión.
     * Por ejemplo: "piso 2, apartamento 5" o "entre X e Y calle"
     * @return La información de referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Obtiene la coordenada de latitud.
     * @return La latitud en grados decimales (entre -90 y 90)
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Obtiene la coordenada de longitud.
     * @return La longitud en grados decimales (entre -180 y 180)
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Genera una descripción corta de la ubicación.
     * Construye una cadena con los componentes principales de la dirección
     * (localidad, ciudad, provincia, país) separados por comas, omitiendo
     * aquellos que estén vacíos o sean nulos.

     * Ejemplo: "Flores, Buenos Aires, Buenos Aires, Argentina"

     * @return Una cadena de texto con la descripción corta de la ubicación
     */
    public String descripcionCorta() {
        StringBuilder sb = new StringBuilder();

        if (localidad != null && !localidad.isBlank()) {
            sb.append(localidad);
        }

        if (ciudad != null && !ciudad.isBlank()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(ciudad);
        }

        if (provincia != null && !provincia.isBlank()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(provincia);
        }

        if (pais != null && !pais.isBlank()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(pais);
        }

        return sb.toString();
    }

    /**
     * Valida si la ubicación tiene coordenadas geográficas válidas.
     * Verifica que tanto latitud como longitud no sean nulas y estén dentro
     * de los rangos permitidos por la norma de coordenadas geográficas:
     * - Latitud: entre -90 y 90 grados
     * - Longitud: entre -180 y 180 grados
     *
     * @return true si ambas coordenadas son válidas, false en caso contrario
     */
    public boolean tieneCoordenadasValidas() {
        return latitud != null
                && longitud != null
                && latitud >= -90.0 && latitud <= 90.0
                && longitud >= -180.0 && longitud <= 180.0;
    }

    /**
     * Determina si la ubicación es apta para realizar búsquedas geográficas.
     * Una ubicación es apta si cumple con al menos uno de estos criterios:
     * 1. Tiene coordenadas válidas (latitud y longitud dentro de rango válido)
     * 2. Tiene información completa de localidad, ciudad y provincia (no nulas ni vacías)

     * Este método es útil para determinar si se puede usar la ubicación en
     * operaciones de búsqueda geográfica o geolocalización.

     * @return true si la ubicación es apta para búsqueda geográfica, false en caso contrario
     */
    public boolean esAptaParaBusquedaGeografica() {
        return tieneCoordenadasValidas()
                || (localidad != null && !localidad.isBlank()
                && ciudad != null && !ciudad.isBlank()
                && provincia != null && !provincia.isBlank());
    }

    /**
     * Compara dos objetos Ubicacion para determinar si son iguales.
     * Dos ubicaciones son iguales si todos sus atributos tienen el mismo valor:
     * país, provincia, ciudad, localidad, calle, altura, referencia, latitud y longitud.

     * @param o El objeto a comparar con esta ubicación
     * @return true si ambas ubicaciones son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ubicacion that)) {
            return false;
        }
        return Objects.equals(pais, that.pais)
                && Objects.equals(provincia, that.provincia)
                && Objects.equals(ciudad, that.ciudad)
                && Objects.equals(localidad, that.localidad)
                && Objects.equals(calle, that.calle)
                && Objects.equals(altura, that.altura)
                && Objects.equals(referencia, that.referencia)
                && Objects.equals(latitud, that.latitud)
                && Objects.equals(longitud, that.longitud);
    }

    /**
     * Calcula el código hash de la ubicación.
     * Genera un código hash basado en todos los atributos de la ubicación.
     * Este método es consistente con equals(), garantizando que dos ubicaciones
     * iguales tendrán el mismo código hash.
     *
     * El hash se utiliza para operaciones en colecciones como HashMap o HashSet.
     *
     * @return El código hash de esta ubicación
     */
    @Override
    public int hashCode() {
        return Objects.hash(pais, provincia, ciudad, localidad, calle, altura, referencia, latitud, longitud);
    }
}