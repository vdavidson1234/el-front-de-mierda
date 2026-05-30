export type RoleType = "client" | "provider" | "both" | null;
export type ApiRole = "ADMIN" | "USUARIO";
export type ApiModality = "PRESENCIAL" | "VIRTUAL" | "MIXTA";

export interface SessionUser {
  id: string;
  name: string;
  email: string;
  role: RoleType;
  accessToken?: string;
  refreshToken?: string;
}

export interface ApiCategory {
  id: string;
  nombre: string;
  descripcion?: string;
  estado?: string;
}

export interface ApiPublication {
  id: string;
  usuarioId?: string;
  categoriaServicioId?: string;
  titulo: string;
  descripcion: string;
  modalidadServicio: ApiModality;
  ubicacion?: ApiLocation;
  disponibilidadesHorarias?: ApiAvailability[];
  precioBase?: number;
  estado?: string;
}

export interface ApiRequest {
  id: string;
  solicitanteId?: string;
  categoriaServicioId?: string;
  modalidadServicio: ApiModality;
  ubicacion?: ApiLocation;
  disponibilidadRequerida?: ApiAvailability;
  descripcionNecesidad: string;
  precioReferencia?: number;
  estado?: string;
  createdAt?: string;
}

export interface ApiReceivedRequest extends ApiRequest {
  distribucionSolicitudId?: string;
  solicitudId?: string;
  publicacionServicioId?: string;
}

export interface ApiAssignmentState {
  solicitudId: string;
  solicitanteId?: string;
  estadoSolicitud?: string;
  asignacion?: {
    id: string;
    solicitudId?: string;
    prestadorId?: string;
    estado?: string;
    fechaFinalizacion?: string;
  };
  contraofertasPendientes?: unknown[];
  distribucionesActivas?: number;
  confirmadoPorSolicitante?: boolean;
  confirmadoPorPrestador?: boolean;
  finalizacionConfirmada?: boolean;
}

export interface ApiLocation {
  pais: string;
  provincia: string;
  ciudad: string;
  localidad: string;
  calle: string;
  altura: string;
  referencia: string;
  latitud: number;
  longitud: number;
}

export interface ApiAvailability {
  diaSemana: string;
  horaDesde: string;
  horaHasta: string;
}

export interface ApiUserProfile {
  id: string;
  usuarioId: string;
  nombre: string;
  apellido: string;
  edad?: number;
  fotoPerfilUrl?: string;
  ubicacion?: ApiLocation;
  descripcionPersonal?: string;
  perfilCompleto?: boolean;
}

export interface ApiAccountConfig {
  usuario: {
    id: string;
    email: string;
    telefono?: string;
    rol?: ApiRole;
  };
  perfil?: ApiUserProfile;
}

export interface ProfilePreferences {
  email?: string;
  availabilityFrom?: string;
  availabilityTo?: string;
}

function getDefaultApiBaseUrl() {
  if (typeof window === "undefined") {
    return "http://localhost:8080/api/v1";
  }

  const host = window.location.hostname || "localhost";
  return `${window.location.protocol}//${host}:8080/api/v1`;
}

const API_BASE_URL =
  import.meta.env.VITE_SERVIFY_API_URL ?? (import.meta.env.DEV ? "/api/v1" : getDefaultApiBaseUrl());

const SESSION_KEY = "servify.session";
const PROFILE_PREFS_KEY = "servify.profile-prefs";

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  let response: Response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers: {
        "Content-Type": "application/json",
        ...(options.headers ?? {}),
      },
    });
  } catch {
    throw new Error(
      `No se pudo conectar con el backend en ${API_BASE_URL}. Verificá que esté levantado y accesible desde este dispositivo.`
    );
  }

  const rawText = await response.text();
  const contentType = response.headers.get("content-type") ?? "";
  const hasJsonBody = rawText.trim().length > 0 && contentType.includes("application/json");

  if (!response.ok) {
    let message = `Error ${response.status}`;

    if (hasJsonBody) {
      try {
        const body = JSON.parse(rawText) as { message?: string; error?: string };
        message = body.message ?? body.error ?? message;
      } catch {
        message = rawText || message;
      }
    } else if (rawText.trim()) {
      message = rawText;
    }

    throw new Error(message);
  }

  if (response.status === 204 || rawText.trim().length === 0) {
    return undefined as T;
  }

  if (!hasJsonBody) {
    return rawText as T;
  }

  return JSON.parse(rawText) as T;
}

export const servifyApi = {
  getStoredSession(): SessionUser | null {
    const raw = localStorage.getItem(SESSION_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as SessionUser;
    } catch {
      localStorage.removeItem(SESSION_KEY);
      return null;
    }
  },

  storeSession(user: SessionUser) {
    localStorage.setItem(SESSION_KEY, JSON.stringify(user));
  },

  clearSession() {
    localStorage.removeItem(SESSION_KEY);
  },

  updateStoredSession(patch: Partial<SessionUser>) {
    const current = this.getStoredSession();
    if (!current) return;
    this.storeSession({ ...current, ...patch });
  },

  getProfilePreferences(userId: string): ProfilePreferences {
    const raw = localStorage.getItem(PROFILE_PREFS_KEY);
    if (!raw) return {};
    try {
      const parsed = JSON.parse(raw) as Record<string, ProfilePreferences>;
      return parsed[userId] ?? {};
    } catch {
      localStorage.removeItem(PROFILE_PREFS_KEY);
      return {};
    }
  },

  saveProfilePreferences(userId: string, preferences: ProfilePreferences) {
    const raw = localStorage.getItem(PROFILE_PREFS_KEY);
    let all: Record<string, ProfilePreferences> = {};
    if (raw) {
      try {
        all = JSON.parse(raw) as Record<string, ProfilePreferences>;
      } catch {
        all = {};
      }
    }
    all[userId] = { ...(all[userId] ?? {}), ...preferences };
    localStorage.setItem(PROFILE_PREFS_KEY, JSON.stringify(all));
  },

  async login(email: string, password: string): Promise<SessionUser> {
    const session = await request<{
      usuarioId: string;
      accessToken?: { token: string };
      refreshToken?: { token: string };
    }>("/auth/login", {
      method: "POST",
      body: JSON.stringify({ emailAcceso: email, passwordPlano: password }),
    });

    const user: SessionUser = {
      id: session.usuarioId,
      name: email.split("@")[0] || "Usuario",
      email,
      role: "both",
      accessToken: session.accessToken?.token,
      refreshToken: session.refreshToken?.token,
    };
    this.storeSession(user);
    return user;
  },

  async register(input: {
    nombre: string;
    apellido: string;
    email: string;
    password: string;
    localidad: string;
    role: RoleType;
  }): Promise<SessionUser> {
    const usuario = await request<{ id: string }>("/usuarios", {
      method: "POST",
      body: JSON.stringify({
        email: input.email,
        telefono: "Sin telefono",
        rol: "USUARIO" satisfies ApiRole,
      }),
    });

    await request<void>("/auth/credenciales", {
      method: "POST",
      body: JSON.stringify({
        usuarioId: usuario.id,
        emailAcceso: input.email,
        passwordPlano: input.password,
      }),
    });

    await request("/usuarios/" + usuario.id + "/perfil", {
      method: "PUT",
      body: JSON.stringify({
        nombre: input.nombre || "Nuevo",
        apellido: input.apellido || "Usuario",
        edad: 25,
        fotoPerfilUrl: "",
        ubicacion: buildLocation(input.localidad),
        descripcionPersonal:
          input.role === "provider" ? "Prestador de servicios" : "Cliente Servify",
      }),
    });

    const user: SessionUser = {
      id: usuario.id,
      name: `${input.nombre || "Nuevo"} ${input.apellido || "Usuario"}`.trim(),
      email: input.email,
      role: input.role,
    };
    this.storeSession(user);
    return user;
  },

  listCategories() {
    return request<ApiCategory[]>("/categorias/activas");
  },

  async ensureCategory(nombre: string): Promise<ApiCategory> {
    const active = await this.listCategories().catch(() => []);
    const existing = active.find((cat) => cat.nombre.toLowerCase() === nombre.toLowerCase());
    if (existing) return existing;

    const created = await request<ApiCategory>("/categorias", {
      method: "POST",
      body: JSON.stringify({ nombre, descripcion: `Servicios de ${nombre}` }),
    });

    return request<ApiCategory>(`/categorias/${created.id}/estado`, {
      method: "PATCH",
      body: JSON.stringify({
        estadoDestino: "ACTIVA",
        motivo: "Disponible desde frontend",
      }),
    });
  },

  async createPublication(input: {
    usuarioId: string;
    categoria: string;
    titulo: string;
    descripcion: string;
    modalidad: string;
    localidad: string;
    direccion: string;
    precio: string;
  }) {
    const category = await this.ensureCategory(input.categoria);
    const publication = await request<ApiPublication>("/publicaciones", {
      method: "POST",
      body: JSON.stringify({
        usuarioId: input.usuarioId,
        categoriaServicioId: category.id,
        titulo: input.titulo,
        descripcion: input.descripcion,
        modalidadServicio: toApiModality(input.modalidad),
        ubicacion: buildLocation(input.localidad, input.direccion),
        disponibilidadesHorarias: [defaultAvailability()],
        precioBase: parseMoney(input.precio),
      }),
    });

    return request<ApiPublication>(`/publicaciones/${publication.id}/estado`, {
      method: "PATCH",
      body: JSON.stringify({
        usuarioId: input.usuarioId,
        estadoDestino: "ACTIVA",
        motivo: "Lista para recibir solicitudes",
      }),
    });
  },

  listUserPublications(usuarioId: string) {
    return request<ApiPublication[]>(`/usuarios/${usuarioId}/publicaciones`);
  },

  async changePublicationState(publicacionId: string, usuarioId: string, active: boolean) {
    return request<ApiPublication>(`/publicaciones/${publicacionId}/estado`, {
      method: "PATCH",
      body: JSON.stringify({
        usuarioId,
        estadoDestino: active ? "ACTIVA" : "PAUSADA",
        motivo: active ? "Activada desde frontend" : "Pausada desde frontend",
      }),
    });
  },

  async createServiceRequest(input: {
    solicitanteId: string;
    categoria: string;
    descripcion: string;
    modalidad: string;
    localidad: string;
    precio: string;
  }) {
    const category = await this.ensureCategory(input.categoria);
    return request<ApiRequest>("/solicitudes", {
      method: "POST",
      body: JSON.stringify({
        solicitanteId: input.solicitanteId,
        categoriaServicioId: category.id,
        modalidadServicio: toApiModality(input.modalidad),
        ubicacion: buildLocation(input.localidad),
        disponibilidadRequerida: defaultAvailability(),
        descripcionNecesidad: input.descripcion,
        precioReferencia: parseMoney(input.precio || "0"),
      }),
    });
  },

  listUserRequests(usuarioId: string) {
    return request<ApiRequest[]>(`/usuarios/${usuarioId}/solicitudes`);
  },

  listReceivedRequests(usuarioId: string) {
    return request<ApiReceivedRequest[]>(`/prestadores/${usuarioId}/solicitudes-recibidas`);
  },

  getAssignmentState(solicitudId: string) {
    return request<ApiAssignmentState>(`/solicitudes/${solicitudId}/estado-asignacion`);
  },

  async confirmServiceCompletion(input: {
    solicitudId: string;
    asignacionServicioId: string;
    confirmanteId: string;
    rolConfirmante: "SOLICITANTE" | "PRESTADOR";
    observacion?: string;
  }) {
    return request<void>(`/solicitudes/${input.solicitudId}/finalizaciones/confirmaciones`, {
      method: "POST",
      body: JSON.stringify({
        asignacionServicioId: input.asignacionServicioId,
        confirmanteId: input.confirmanteId,
        rolConfirmante: input.rolConfirmante,
        observacion: input.observacion ?? "",
      }),
    });
  },

  getAccountConfig(userId: string) {
    return request<ApiAccountConfig>(`/usuarios/${userId}/cuenta`);
  },

  getUserProfile(userId: string) {
    return request<ApiUserProfile>(`/usuarios/${userId}/perfil`);
  },

  updateUserProfile(
    userId: string,
    input: {
      nombre: string;
      apellido: string;
      edad?: number;
      fotoPerfilUrl?: string;
      localidad: string;
      descripcionPersonal?: string;
    }
  ) {
    return request<ApiUserProfile>(`/usuarios/${userId}/perfil`, {
      method: "PUT",
      body: JSON.stringify({
        nombre: input.nombre,
        apellido: input.apellido,
        edad: input.edad ?? 25,
        fotoPerfilUrl: input.fotoPerfilUrl ?? "",
        ubicacion: buildLocation(input.localidad),
        descripcionPersonal: input.descripcionPersonal ?? "",
      }),
    });
  },
};

export function buildLocation(localidad = "CABA", direccion = ""): ApiLocation {
  const [calle, altura] = splitAddress(direccion);
  return {
    pais: "Argentina",
    provincia: "Buenos Aires",
    ciudad: "CABA",
    localidad: localidad || "CABA",
    calle,
    altura,
    referencia: direccion,
    latitud: -34.58,
    longitud: -58.42,
  };
}

export function defaultAvailability(): ApiAvailability {
  return {
    diaSemana: "MONDAY",
    horaDesde: "09:00:00",
    horaHasta: "18:00:00",
  };
}

export function toApiModality(modality?: string | null): ApiModality {
  if (modality === "Virtual") return "VIRTUAL";
  if (modality === "Ambas") return "MIXTA";
  return "PRESENCIAL";
}

export function fromApiModality(modality?: string): "Presencial" | "Virtual" | "Ambas" {
  if (modality === "VIRTUAL") return "Virtual";
  if (modality === "MIXTA") return "Ambas";
  return "Presencial";
}

export function parseMoney(value: string): number {
  const normalized = value.replace(/[^\d,.-]/g, "").replace(/\./g, "").replace(",", ".");
  const parsed = Number(normalized);
  return Number.isFinite(parsed) && parsed > 0 ? parsed : 0;
}

export function formatMoney(value?: number): string {
  if (!value) return "A convenir";
  return new Intl.NumberFormat("es-AR", {
    style: "currency",
    currency: "ARS",
    maximumFractionDigits: 0,
  }).format(value);
}

function splitAddress(address: string): [string, string] {
  const match = address.match(/^(.*?)(\d+.*)?$/);
  return [match?.[1]?.trim() || "Sin calle", match?.[2]?.trim() || "S/N"];
}
