# Guia API React MVP - Servify

Fecha: 27 de mayo de 2026

## Base URL

Backend local:

```text
http://localhost:8080/api/v1
```

Ejemplo con Axios:

```javascript
import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json"
  }
});
```

CORS ya queda habilitado para:

```text
http://localhost:3000
http://localhost:5173
http://127.0.0.1:3000
http://127.0.0.1:5173
```

## Como levantar el backend

Comando esperado:

```powershell
.\mvnw.cmd spring-boot:run
```

En esta maquina el wrapper de PowerShell habia fallado. Si vuelve a pasar, usar el Maven que ya bajo el wrapper:

```powershell
& 'C:\Users\juanc\.m2\wrapper\dists\apache-maven-3.9.14-bin\1cb7fhup6b5n3bed6kckbrnspv\apache-maven-3.9.14\bin\mvn.cmd' spring-boot:run
```

Mientras la base definitiva no este lista, el backend usa adapters en memoria:

```properties
servify.adapters.memory.enabled=true
```

Los datos se pierden al reiniciar la aplicacion. Cuando entren los adapters JPA reales, se debe poner:

```properties
servify.adapters.memory.enabled=false
```

## Convenciones JSON

Enviar siempre:

```http
Content-Type: application/json
```

Enums esperados:

```text
Rol: ADMIN, USUARIO
ModalidadServicio: PRESENCIAL, VIRTUAL, MIXTA
EstadoCategoria: ACTIVA, INACTIVA
EstadoPublicacion: ACTIVA, INACTIVA, PAUSADA, BLOQUEADA, ELIMINADA
TipoRespuestaDistribucion: ACEPTAR, RECHAZAR
TipoDecisionSolicitud: ACEPTAR, RECHAZAR
RolConfirmante: SOLICITANTE, PRESTADOR
DayOfWeek: MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
```

Formato de hora:

```json
"09:00:00"
```

## Flujo MVP Para React

### 1. Crear usuario

Pantalla sugerida: registro.

```http
POST /usuarios
```

Body:

```json
{
  "email": "cliente@servify.test",
  "telefono": "1111",
  "rol": "USUARIO"
}
```

React debe guardar:

```text
response.id -> usuarioId
```

### 2. Registrar credenciales

Pantalla sugerida: registro / set password.

```http
POST /auth/credenciales
```

Body:

```json
{
  "usuarioId": "UUID_DEL_USUARIO",
  "emailAcceso": "cliente@servify.test",
  "passwordPlano": "123456"
}
```

Respuesta: `201 Created` sin body.

### 3. Login

Pantalla sugerida: login.

```http
POST /auth/login
```

Body:

```json
{
  "emailAcceso": "cliente@servify.test",
  "passwordPlano": "123456"
}
```

React debe guardar:

```text
response.usuarioId
response.accessToken.token
response.refreshToken.token
```

Nota: el MVP emite tokens, pero todavia no hay filtro de seguridad aplicado a los endpoints.

### 4. Completar perfil de prestador

Pantalla sugerida: onboarding de prestador.

```http
PUT /usuarios/{usuarioId}/perfil
```

Body:

```json
{
  "nombre": "Ana",
  "apellido": "Gomez",
  "edad": 32,
  "fotoPerfilUrl": "https://cdn.servify.test/ana.jpg",
  "ubicacion": {
    "pais": "Argentina",
    "provincia": "Buenos Aires",
    "ciudad": "CABA",
    "localidad": "Palermo",
    "calle": "Santa Fe",
    "altura": "1234",
    "referencia": "PB",
    "latitud": -34.58,
    "longitud": -58.42
  },
  "descripcionPersonal": "Plomeria general"
}
```

React puede usar:

```http
GET /usuarios/{usuarioId}/perfil
GET /usuarios/{usuarioId}/cuenta
```

### 5. Categorias

Pantalla sugerida: seed/admin simple o panel interno del prototipo.

Crear categoria:

```http
POST /categorias
```

Body:

```json
{
  "nombre": "Plomeria",
  "descripcion": "Arreglos domesticos"
}
```

Activar categoria:

```http
PATCH /categorias/{categoriaId}/estado
```

Body:

```json
{
  "estadoDestino": "ACTIVA",
  "motivo": "Preparacion MVP"
}
```

Listar categorias activas:

```http
GET /categorias/activas
```

React debe usar `categoria.id` para crear publicaciones y solicitudes.

### 6. Crear publicacion del prestador

Pantalla sugerida: alta de servicio ofrecido.

```http
POST /publicaciones
```

Body:

```json
{
  "usuarioId": "UUID_PRESTADOR",
  "categoriaServicioId": "UUID_CATEGORIA",
  "titulo": "Plomera a domicilio",
  "descripcion": "Reparaciones y mantenimiento",
  "modalidadServicio": "PRESENCIAL",
  "ubicacion": {
    "pais": "Argentina",
    "provincia": "Buenos Aires",
    "ciudad": "CABA",
    "localidad": "Palermo",
    "calle": "Santa Fe",
    "altura": "1234",
    "referencia": "PB",
    "latitud": -34.58,
    "longitud": -58.42
  },
  "disponibilidadesHorarias": [
    {
      "diaSemana": "MONDAY",
      "horaDesde": "09:00:00",
      "horaHasta": "12:00:00"
    }
  ],
  "precioBase": 1000
}
```

React debe guardar:

```text
response.id -> publicacionId
```

Activar publicacion:

```http
PATCH /publicaciones/{publicacionId}/estado
```

Body:

```json
{
  "usuarioId": "UUID_PRESTADOR",
  "estadoDestino": "ACTIVA",
  "motivo": "Lista para recibir solicitudes"
}
```

Consultas utiles:

```http
GET /publicaciones/{publicacionId}
GET /usuarios/{usuarioId}/publicaciones
```

### 7. Crear solicitud del cliente

Pantalla sugerida: formulario de pedido de servicio.

```http
POST /solicitudes
```

Body:

```json
{
  "solicitanteId": "UUID_CLIENTE",
  "categoriaServicioId": "UUID_CATEGORIA",
  "modalidadServicio": "PRESENCIAL",
  "ubicacion": {
    "pais": "Argentina",
    "provincia": "Buenos Aires",
    "ciudad": "CABA",
    "localidad": "Palermo",
    "calle": "Santa Fe",
    "altura": "1400",
    "referencia": "Depto 2",
    "latitud": -34.58,
    "longitud": -58.42
  },
  "disponibilidadRequerida": {
    "diaSemana": "MONDAY",
    "horaDesde": "10:00:00",
    "horaHasta": "11:00:00"
  },
  "descripcionNecesidad": "Necesito reparar una perdida",
  "precioReferencia": 1500
}
```

React debe guardar:

```text
response.id -> solicitudId
```

Al crear la solicitud, el backend dispara la distribucion inicial a publicaciones compatibles.

Consultas utiles:

```http
GET /solicitudes/{solicitudId}
GET /usuarios/{solicitanteId}/solicitudes
GET /solicitudes/{solicitudId}/estado-asignacion
```

### 8. Bandeja del prestador

Pantalla sugerida: solicitudes recibidas.

```http
GET /prestadores/{prestadorId}/solicitudes-recibidas
```

React debe guardar de cada item:

```text
item.distribucionSolicitudId -> requerido para aceptar, rechazar o contraofertar
item.solicitudId
item.publicacionServicioId
```

### 9. Responder una distribucion

Pantalla sugerida: detalle de solicitud recibida.

Aceptar o rechazar:

```http
POST /distribuciones/{distribucionSolicitudId}/respuestas
```

Body:

```json
{
  "prestadorId": "UUID_PRESTADOR",
  "tipoRespuesta": "ACEPTAR"
}
```

Respuesta: `204 No Content`.

### 10. Contraoferta

Pantalla sugerida: detalle de solicitud recibida, opcion "proponer otro precio".

Emitir contraoferta:

```http
POST /distribuciones/{distribucionSolicitudId}/contraofertas
```

Body:

```json
{
  "prestadorId": "UUID_PRESTADOR",
  "precioPropuesto": 1800,
  "mensaje": "Incluye materiales"
}
```

Respuesta: `202 Accepted`.

El cliente obtiene la contraoferta pendiente desde:

```http
GET /solicitudes/{solicitudId}/estado-asignacion
```

React debe leer:

```text
response.contraofertasPendientes[].id -> contraofertaId
```

Resolver contraoferta:

```http
POST /contraofertas/{contraofertaId}/resoluciones
```

Body:

```json
{
  "solicitanteId": "UUID_CLIENTE",
  "decision": "ACEPTAR"
}
```

Respuesta: `ContraofertaResult`.

### 11. Confirmar asignacion

Pantalla sugerida: cliente elige prestador aceptado o contraoferta aceptada.

```http
POST /solicitudes/{solicitudId}/asignaciones/confirmaciones
```

Body:

```json
{
  "distribucionSolicitudId": "UUID_DISTRIBUCION",
  "solicitanteId": "UUID_CLIENTE"
}
```

React debe guardar:

```text
response.id -> asignacionServicioId
response.prestadorId
```

### 12. Confirmar finalizacion

Pantalla sugerida: cierre de trabajo para cliente y prestador.

Debe llamarse una vez por cada parte.

```http
POST /solicitudes/{solicitudId}/finalizaciones/confirmaciones
```

Body cliente:

```json
{
  "asignacionServicioId": "UUID_ASIGNACION",
  "confirmanteId": "UUID_CLIENTE",
  "rolConfirmante": "SOLICITANTE",
  "observacion": "Trabajo recibido"
}
```

Body prestador:

```json
{
  "asignacionServicioId": "UUID_ASIGNACION",
  "confirmanteId": "UUID_PRESTADOR",
  "rolConfirmante": "PRESTADOR",
  "observacion": "Trabajo realizado"
}
```

Cuando estan ambas confirmaciones, la solicitud pasa a `FINALIZADA`.

### 13. Calificar servicio

Pantalla sugerida: calificacion posterior a finalizacion.

```http
POST /solicitudes/{solicitudId}/calificaciones
```

Body:

```json
{
  "asignacionServicioId": "UUID_ASIGNACION",
  "solicitanteId": "UUID_CLIENTE",
  "prestadorId": "UUID_PRESTADOR",
  "puntaje": 5
}
```

Respuesta: `201 Created` sin body.

### 14. Cancelar solicitud

Pantalla sugerida: detalle de solicitud del cliente.

```http
DELETE /solicitudes/{solicitudId}
```

Body:

```json
{
  "solicitanteId": "UUID_CLIENTE"
}
```

Respuesta: `204 No Content`.

## Endpoints administrativos MVP

Configuracion vigente:

```http
GET /admin/configuracion
```

Moderacion de publicacion:

```http
PATCH /admin/publicaciones/{publicacionId}/moderacion
```

Body:

```json
{
  "administradorId": "UUID_ADMIN",
  "estadoDestino": "BLOQUEADA",
  "motivo": "Revision manual"
}
```

## Tests HTTP agregados

La conexion REST del flujo principal queda cubierta por:

```text
src/test/java/com/servify/MvpHttpFlowTests.java
```

Ese test valida que el front pueda obtener los IDs necesarios:

```text
usuarioId
categoriaId
publicacionId
solicitudId
distribucionSolicitudId
asignacionServicioId
```

Resultado verificado:

```text
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
