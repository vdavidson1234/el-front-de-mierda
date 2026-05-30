# Informe de tests MVP - Servify

Fecha: 26 de mayo de 2026

## Resumen

Se agrego una suite ordenada para validar el flujo principal del MVP desde las capas de dominio y aplicacion, usando puertos fake en memoria. La suite evita depender de una base de datos real porque la infraestructura definitiva todavia no esta terminada.

Archivo principal de la suite:

```text
src/test/java/com/servify/MvpApplicationFlowTests.java
```

Test de arranque existente:

```text
src/test/java/com/servify/ServifyApplicationTests.java
```

Test HTTP agregado para endpoints del MVP:

```text
src/test/java/com/servify/MvpHttpFlowTests.java
```

Resultado verificado:

```text
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Como ejecutar los tests

Comando esperado del proyecto:

```powershell
.\mvnw.cmd test
```

En esta maquina el wrapper fallo al intentar iniciar Maven desde el script de PowerShell. Como Maven tampoco estaba disponible en el PATH, se ejecuto directamente el Maven descargado por el wrapper:

```powershell
& 'C:\Users\juanc\.m2\wrapper\dists\apache-maven-3.9.14-bin\1cb7fhup6b5n3bed6kckbrnspv\apache-maven-3.9.14\bin\mvn.cmd' test
```

Si aparece un error de permisos o red al resolver dependencias, hay que permitir que Maven acceda una vez al repositorio local/remoto. Despues de resolver dependencias, la suite queda lista para ejecutarse normalmente.

## Tests agregados

### 1. `flujoPrincipalMvp_conAceptacionDirecta_finalizaYCalificaServicio`

Valida el flujo feliz completo del MVP:

1. Se crea un usuario solicitante.
2. Se crea un usuario prestador.
3. El prestador completa su perfil.
4. Se crea y activa una categoria.
5. Se crea y activa una publicacion de servicio.
6. El solicitante crea una solicitud.
7. La solicitud se distribuye automaticamente a publicaciones compatibles.
8. El prestador acepta la distribucion.
9. El solicitante confirma la asignacion.
10. Ambas partes confirman la finalizacion del servicio.
11. El solicitante califica el servicio finalizado.

Que verifica:

- Que los servicios de usuarios, perfiles, categorias, publicaciones, solicitudes, distribucion, asignacion, confirmacion y calificacion puedan trabajar juntos.
- Que una publicacion activa y compatible participe en la distribucion.
- Que una solicitud creada dispare la distribucion cuando se inyectan los puertos necesarios.
- Que la asignacion quede confirmable despues de aceptar una distribucion.
- Que la calificacion solo se registre sobre una asignacion finalizada.

### 2. `contraofertaAceptada_dejaDistribucionAceptadaYConfirmable`

Valida el flujo de contraoferta:

1. Se crea una distribucion enviada.
2. El prestador emite una contraoferta.
3. El solicitante acepta la contraoferta.
4. La distribucion queda en estado aceptado.

Que verifica:

- Que una distribucion en estado `CONTRAOFERTADA` pueda pasar correctamente a `ACEPTADA`.
- Que aceptar una contraoferta no intente usar la transicion comun de una distribucion `ENVIADA`.
- Que la contraoferta quede marcada como aceptada.

Este test cubre la regresion corregida en `ResolverContraofertaService` y `DistribucionSolicitud`.

### 3. `loginFallido_persisteIntentoFallidoAntesDeRechazar`

Valida el flujo de autenticacion ante password incorrecto:

1. Se registra una credencial.
2. Se intenta iniciar sesion con una password incorrecta.
3. El servicio rechaza el login.
4. El intento fallido queda persistido.

Que verifica:

- Que `registrarIntentoFallido()` no quede solamente en memoria.
- Que `IniciarSesionService` guarde la credencial actualizada antes de lanzar el error.
- Que el contador de intentos fallidos aumente despues del rechazo.

### 4. `moderacion_aceptaEstadosRealesDePublicacion`

Valida la moderacion administrativa de publicaciones:

1. Se ejecuta una moderacion con estado destino `BLOQUEADA`.
2. El servicio acepta el estado.
3. El puerto de moderacion recibe el cambio.

Que verifica:

- Que `ModerarPublicacionService` use estados reales de `EstadoPublicacion`.
- Que estados validos como `BLOQUEADA` no sean rechazados por una lista de estados administrativa incorrecta.

### 5. `publicacionInactiva_conDatosValidosPuedeActivarse`

Valida la activacion de una publicacion:

1. Se crea una publicacion inactiva con datos minimos validos.
2. Se ejecuta `activar()`.
3. La publicacion queda activa.

Que verifica:

- Que `activar()` no dependa de `puedeParticiparEnDistribucion()`.
- Que una publicacion inactiva pero completa pueda activarse.
- Que `puedeParticiparEnDistribucion()` siga exigiendo que la publicacion ya este activa.

### 6. `ServifyApplicationTests.contextLoads`

Es el smoke test de Spring Boot existente.

Que verifica:

- Que el contexto de Spring levante correctamente.
- Que la configuracion actual del proyecto no rompa el arranque basico.
- Que H2 permita pasar el arranque mientras la base definitiva sigue pendiente.

## Como trabaja la suite

La suite `MvpApplicationFlowTests` no usa controladores REST ni repositorios JPA reales. En su lugar, arma los casos de uso directamente e inyecta implementaciones fake de los puertos.

Los fakes principales estan definidos como clases internas del archivo de test:

```text
UsuarioRepo
PerfilRepo
CategoriaRepo
PublicacionRepo
SolicitudRepo
DistribucionRepo
AsignacionRepo
ConfirmacionRepo
CalificacionRepo
ContraofertaRepo
CredencialRepo
RefreshTokenRepo
```

Tambien se incluyen adaptadores fake para cruces entre modulos:

```text
UsuarioPublicadorPort
PublicacionesCompatiblesPort
ConfiguracionDistribucionPort
UsuarioAutenticablePort
PasswordHasherPort
TokenProviderPort
UsuarioAdministrablePort
PublicacionModerablePort
```

Cada repositorio fake guarda entidades en `Map<UUID, Entidad>`. Esto permite validar comportamiento de aplicacion sin depender de una base de datos, migraciones, configuracion de datasource ni adapters incompletos.

La idea de esta suite es cubrir el contrato funcional del MVP. Cuando existan adapters reales, estos mismos flujos se pueden complementar con tests de integracion HTTP/JPA.

## Bugs cubiertos por la suite

### Activacion de publicaciones

Antes, una publicacion inactiva no podia activarse porque `activar()` usaba una validacion que tambien exigia que la publicacion ya estuviera activa.

Solucion aplicada:

- Se separo la validacion de datos minimos de la validacion de participacion en distribucion.
- `activar()` valida condiciones minimas.
- `puedeParticiparEnDistribucion()` valida condiciones minimas y estado activo.

### Aceptacion de contraofertas

Antes, aceptar una contraoferta intentaba aceptar la distribucion con la regla de una distribucion enviada. Eso fallaba porque la distribucion estaba en estado `CONTRAOFERTADA`.

Solucion aplicada:

- Se agrego una transicion especifica para aceptar contraofertas.
- `ResolverContraofertaService` usa esa transicion cuando el solicitante acepta.

### Persistencia de intentos fallidos de login

Antes, el contador de intentos fallidos se modificaba en memoria, pero no se guardaba antes de rechazar el login.

Solucion aplicada:

- `IniciarSesionService` guarda la credencial despues de registrar el intento fallido y antes de lanzar el error.

### Moderacion de publicaciones

Antes, el servicio administrativo aceptaba estados que no pertenecian al enum real de publicaciones y rechazaba estados reales como `BLOQUEADA`.

Solucion aplicada:

- `ModerarPublicacionService` valida contra los estados reales de `EstadoPublicacion`.

### Distribucion al crear solicitudes

Antes, crear una solicitud solamente la guardaba. No invocaba los puertos de compatibilidad, configuracion ni motor de distribucion.

Solucion aplicada:

- `CrearSolicitudServicioService` conserva el constructor simple.
- Se agrego un constructor extendido para inyectar dependencias de distribucion.
- Cuando esas dependencias existen, al crear la solicitud se generan y guardan las distribuciones iniciales.

## Alcance actual

Cubierto:

- Reglas de dominio principales.
- Orquestacion de servicios de aplicacion.
- Flujo feliz del MVP.
- Regresiones puntuales detectadas durante la revision.
- Trabajo entre modulos usando puertos.

No cubierto todavia:

- Endpoints REST.
- Serializacion/deserializacion HTTP.
- Repositorios JPA reales.
- Consultas SQL reales.
- Migraciones de base de datos.
- Seguridad real con filtros/JWT.
- Transacciones reales.
- Concurrencia o condiciones de carrera.

## Proximos tests recomendados

Cuando esten terminados los adapters de infraestructura, conviene agregar:

1. Tests de repositorios con `@DataJpaTest`.
2. Tests de API con `@SpringBootTest` y `MockMvc`.
3. Tests de seguridad para login, refresh token y autorizacion por rol.
4. Tests de migraciones contra la base definitiva.
5. Tests end-to-end del flujo completo desde HTTP hasta persistencia.
