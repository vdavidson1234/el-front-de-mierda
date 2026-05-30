package com.servify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ServifyApplication.class)
@AutoConfigureMockMvc
class MvpHttpFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void flujoPrincipalMvp_exponeIdsNecesariosParaReact() throws Exception {
        UUID solicitanteId = crearUsuario("cliente-http@servify.test");
        UUID prestadorId = crearUsuario("prestador-http@servify.test");

        mockMvc.perform(putJson("/api/v1/usuarios/" + prestadorId + "/perfil", """
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
                """))
                .andExpect(status().isOk());

        UUID categoriaId = idFrom(postCreatedPath("/api/v1/categorias", """
                {
                  "nombre": "Plomeria HTTP",
                  "descripcion": "Arreglos domesticos"
                }
                """));

        mockMvc.perform(patchJson("/api/v1/categorias/" + categoriaId + "/estado", """
                {
                  "estadoDestino": "ACTIVA",
                  "motivo": "Preparacion MVP"
                }
                """))
                .andExpect(status().isOk());

        UUID publicacionId = idFrom(postCreatedPath("/api/v1/publicaciones", """
                {
                  "usuarioId": "%s",
                  "categoriaServicioId": "%s",
                  "titulo": "Plomera a domicilio HTTP",
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
                """.formatted(prestadorId, categoriaId)));

        mockMvc.perform(patchJson("/api/v1/publicaciones/" + publicacionId + "/estado", """
                {
                  "usuarioId": "%s",
                  "estadoDestino": "ACTIVA",
                  "motivo": "Lista para recibir solicitudes"
                }
                """.formatted(prestadorId)))
                .andExpect(status().isOk());

        UUID solicitudId = idFrom(postCreatedPath("/api/v1/solicitudes", """
                {
                  "solicitanteId": "%s",
                  "categoriaServicioId": "%s",
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
                """.formatted(solicitanteId, categoriaId)));

        JsonNode recibidas = responseJson(getPath("/api/v1/prestadores/" + prestadorId + "/solicitudes-recibidas"));
        UUID distribucionId = UUID.fromString(recibidas.get(0).get("distribucionSolicitudId").asText());

        mockMvc.perform(postJson("/api/v1/distribuciones/" + distribucionId + "/respuestas", """
                {
                  "prestadorId": "%s",
                  "tipoRespuesta": "ACEPTAR"
                }
                """.formatted(prestadorId)))
                .andExpect(status().isNoContent());

        JsonNode asignacion = responseJson(postPath("/api/v1/solicitudes/" + solicitudId + "/asignaciones/confirmaciones", """
                {
                  "distribucionSolicitudId": "%s",
                  "solicitanteId": "%s"
                }
                """.formatted(distribucionId, solicitanteId)));

        UUID asignacionId = UUID.fromString(asignacion.get("id").asText());
        mockMvc.perform(get("/api/v1/solicitudes/" + solicitudId + "/estado-asignacion"))
                .andExpect(status().isOk());

        assertNotNull(asignacionId);
    }

    private UUID crearUsuario(String email) throws Exception {
        return idFrom(postCreatedPath("/api/v1/usuarios", """
                {
                  "email": "%s",
                  "telefono": "1111",
                  "rol": "USUARIO"
                }
                """.formatted(email)));
    }

    private UUID idFrom(String json) throws Exception {
        return UUID.fromString(objectMapper.readTree(json).get("id").asText());
    }

    private JsonNode responseJson(String json) throws Exception {
        return objectMapper.readTree(json);
    }

    private String getPath(String path) throws Exception {
        return mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private String postPath(String path, String json) throws Exception {
        return mockMvc.perform(postJson(path, json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private String postCreatedPath(String path, String json) throws Exception {
        return mockMvc.perform(postJson(path, json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private org.springframework.test.web.servlet.RequestBuilder postJson(String path, String json) {
        return post(path).contentType(MediaType.APPLICATION_JSON).content(json);
    }

    private org.springframework.test.web.servlet.RequestBuilder putJson(String path, String json) {
        return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    }

    private org.springframework.test.web.servlet.RequestBuilder patchJson(String path, String json) {
        return patch(path).contentType(MediaType.APPLICATION_JSON).content(json);
    }
}
