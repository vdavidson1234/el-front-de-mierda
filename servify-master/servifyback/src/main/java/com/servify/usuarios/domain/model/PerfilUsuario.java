package com.servify.usuarios.domain.model;

import java.util.UUID;

import com.servify.shared.domain.exception.ValidationException;
import com.servify.shared.domain.model.BaseEntity;
import com.servify.shared.domain.valueobject.Ubicacion;
import com.servify.usuarios.domain.valueobject.NombreCompleto;

/**
 * Entidad que representa el perfil de usuario en el sistema Servify.
 * Contiene informacion personal como nombre, edad, foto, ubicacion y descripcion.
 * Extiende BaseEntity para heredar funcionalidad comun.
 */
public class PerfilUsuario extends BaseEntity {

    private static final int EDAD_MINIMA_PERMITIDA = 18;
    private static final int EDAD_MAXIMA_PERMITIDA = 120;
    private static final int LONGITUD_MAXIMA_DESCRIPCION = 500;

    private UUID usuarioId;
    private NombreCompleto nombreCompleto;
    private Integer edad;
    private String fotoPerfilUrl;
    private Ubicacion ubicacion;
    private String descripcionPersonal;
    private Boolean perfilCompleto;

    protected PerfilUsuario() {
    }

    /**
     * Crea un perfil de usuario con todos sus datos.
     */
    public PerfilUsuario(UUID id,
                         UUID usuarioId,
                         NombreCompleto nombreCompleto,
                         Integer edad,
                         String fotoPerfilUrl,
                         Ubicacion ubicacion,
                         String descripcionPersonal,
                         Boolean perfilCompleto) {
        super(id);
        this.usuarioId = validarUsuarioId(usuarioId);
        this.nombreCompleto = validarNombreCompleto(nombreCompleto);
        this.edad = validarEdad(edad);
        this.fotoPerfilUrl = normalizarFotoPerfil(fotoPerfilUrl);
        this.ubicacion = validarUbicacion(ubicacion);
        this.descripcionPersonal = normalizarDescripcionPersonal(descripcionPersonal);
        this.perfilCompleto = Boolean.TRUE.equals(perfilCompleto);
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getDescripcionPersonal() {
        return descripcionPersonal;
    }

    public Boolean getPerfilCompleto() {
        return perfilCompleto;
    }

    public void actualizarNombre(String nombre, String apellido) {
        // Valida nombre y apellido, construye el value object y actualiza la referencia en la entidad.
        NombreCompleto nuevoNombreCompleto = new NombreCompleto(
                normalizarTextoObligatorio(nombre, "El nombre es obligatorio"),
                normalizarTextoObligatorio(apellido, "El apellido es obligatorio")
        );

        if (!nuevoNombreCompleto.esValido()) {
            throw new ValidationException("El nombre completo no es valido");
        }

        this.nombreCompleto = nuevoNombreCompleto;
    }

    public void actualizarEdad(Integer edad) {
        // Se permite null para representar un perfil incompleto, pero si existe debe estar en rango.
        this.edad = validarEdad(edad);
    }

    public void actualizarFotoPerfil(String fotoPerfilUrl) {
        // La foto se normaliza. Si viene vacia se guarda como null.
        this.fotoPerfilUrl = normalizarFotoPerfil(fotoPerfilUrl);
    }

    public void actualizarUbicacion(Ubicacion ubicacion) {
        // La ubicacion puede ser null, pero si existe debe ser apta para busquedas geograficas.
        this.ubicacion = validarUbicacion(ubicacion);
    }

    public void actualizarDescripcionPersonal(String descripcionPersonal) {
        // La descripcion es opcional y se limita su longitud maxima.
        this.descripcionPersonal = normalizarDescripcionPersonal(descripcionPersonal);
    }

    public boolean estaCompleto() {
        // Regla basica de completitud del perfil.
        return tieneNombreCompleto()
                && tieneEdadValida()
                && tieneFotoPerfil()
                && tieneUbicacionValida();
    }

    public void recalcularEstadoPerfilCompleto() {
        // Recalcula la bandera interna usando la regla basica de la propia entidad.
        this.perfilCompleto = estaCompleto();
    }

    public void actualizarEstadoPerfilCompleto(boolean perfilCompleto) {
        // Permite reflejar en la entidad el resultado de PoliticaPerfilCompleto.
        this.perfilCompleto = perfilCompleto;
    }

    public boolean tieneNombreCompleto() {
        return nombreCompleto != null && nombreCompleto.esValido();
    }

    public boolean tieneEdadValida() {
        return edad != null
                && edad >= EDAD_MINIMA_PERMITIDA
                && edad <= EDAD_MAXIMA_PERMITIDA;
    }

    public boolean tieneFotoPerfil() {
        return fotoPerfilUrl != null && !fotoPerfilUrl.isBlank();
    }

    public boolean tieneUbicacionValida() {
        return ubicacion != null && ubicacion.esAptaParaBusquedaGeografica();
    }

    private UUID validarUsuarioId(UUID usuarioId) {
        if (usuarioId == null) {
            throw new ValidationException("El usuarioId del perfil es obligatorio");
        }

        return usuarioId;
    }

    private NombreCompleto validarNombreCompleto(NombreCompleto nombreCompleto) {
        if (nombreCompleto != null && !nombreCompleto.esValido()) {
            throw new ValidationException("El nombre completo no es valido");
        }

        return nombreCompleto;
    }

    private Integer validarEdad(Integer edad) {
        if (edad != null && (edad < EDAD_MINIMA_PERMITIDA || edad > EDAD_MAXIMA_PERMITIDA)) {
            throw new ValidationException(
                    "La edad debe estar entre " + EDAD_MINIMA_PERMITIDA + " y " + EDAD_MAXIMA_PERMITIDA
            );
        }

        return edad;
    }

    private String normalizarFotoPerfil(String fotoPerfilUrl) {
        return normalizarTextoOpcional(fotoPerfilUrl);
    }

    private Ubicacion validarUbicacion(Ubicacion ubicacion) {
        if (ubicacion != null && !ubicacion.esAptaParaBusquedaGeografica()) {
            throw new ValidationException("La ubicacion no es valida para operar en la plataforma");
        }

        return ubicacion;
    }

    private String normalizarDescripcionPersonal(String descripcionPersonal) {
        String descripcionNormalizada = normalizarTextoOpcional(descripcionPersonal);

        if (descripcionNormalizada != null && descripcionNormalizada.length() > LONGITUD_MAXIMA_DESCRIPCION) {
            throw new ValidationException(
                    "La descripcion personal no puede superar los " + LONGITUD_MAXIMA_DESCRIPCION + " caracteres"
            );
        }

        return descripcionNormalizada;
    }

    private String normalizarTextoOpcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }

    private String normalizarTextoObligatorio(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }

        return valor.trim();
    }
}
