import { useEffect, useState } from "react";
import { Plus, MapPin, Users, DollarSign } from "lucide-react";
import { motion } from "motion/react";
import { formatMoney, fromApiModality, servifyApi, type ApiRequest } from "../api";

type RequestTab = "my-requests" | "my-proposals" | "explore";

export interface ServiceRequest {
  id: number | string;
  viewerRole?: "SOLICITANTE" | "PRESTADOR" | null;
  title: string;
  description: string;
  category: string;
  location: string;
  proposals: number;
  price: string;
  date: string;
  status: "open" | "completed" | "cancelled" | "in-progress";
  requesterName: string;
  requesterInitials: string;
  modal: "Presencial" | "Virtual" | "Ambas";
}

// Demo placeholders removed to avoid showing fake proposals to external users.

const statusConfig = {
  open: { label: "Abierta", bg: "#eff6ff", color: "#2563eb" },
  completed: { label: "Completada", bg: "#f0fdf4", color: "#16a34a" },
  cancelled: { label: "Cancelada", bg: "#fef2f2", color: "#ef4444" },
  "in-progress": { label: "En curso", bg: "#fffbeb", color: "#d97706" },
};

interface RequestsScreenProps {
  userId?: string;
  onRequestPress: (req: ServiceRequest) => void;
  onNewRequest: () => void;
}

export function RequestsScreen({ userId, onRequestPress, onNewRequest }: RequestsScreenProps) {
  const [tab, setTab] = useState<RequestTab>("my-requests");
  const [apiRequests, setApiRequests] = useState<ServiceRequest[]>([]);
  const [apiReceived, setApiReceived] = useState<ServiceRequest[]>([]);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!userId) return;
    let ignore = false;
    setError("");

    Promise.all([
      servifyApi.listUserRequests(userId).catch(() => []),
      servifyApi.listReceivedRequests(userId).catch(() => []),
    ])
      .then(([own, received]) => {
        if (ignore) return;
        setApiRequests(own.map((req) => mapRequest(req, "SOLICITANTE")));
        setApiReceived(received.map((req) => mapRequest(req, "PRESTADOR")));
      })
      .catch((err) => {
        if (!ignore) setError(err instanceof Error ? err.message : "No se pudieron cargar las solicitudes");
      });

    return () => {
      ignore = true;
    };
  }, [userId]);

  const tabs: { id: RequestTab; label: string }[] = [
    { id: "my-requests", label: "Mis pedidos" },
    { id: "my-proposals", label: "Mis propuestas" },
    { id: "explore", label: "Explorar" },
  ];

  const data = tab === "my-requests" ? apiRequests : tab === "my-proposals" ? apiReceived : [];

  return (
    <div className="flex flex-col h-full" style={{ background: "#f8fafc" }}>
      {/* Header */}
      <div className="px-5 pt-12 pb-0 bg-white">
        <div className="flex items-center justify-between mb-4">
          <h1 style={{ fontSize: 24, fontWeight: 800, color: "#0f172a" }}>Solicitudes</h1>
          <button
            onClick={onNewRequest}
            className="flex items-center gap-1.5 px-4 py-2 rounded-xl transition-all active:scale-95"
            style={{ background: "#2563eb", color: "white", fontWeight: 700, fontSize: 13 }}
          >
            <Plus size={16} strokeWidth={2.5} />
            Nueva
          </button>
        </div>

        {/* Tabs */}
        <div className="flex gap-1">
          {tabs.map(({ id, label }) => (
            <button
              key={id}
              onClick={() => setTab(id)}
              className="px-3.5 py-2.5 rounded-t-xl transition-all"
              style={{
                background: tab === id ? "#f8fafc" : "transparent",
                color: tab === id ? "#2563eb" : "#94a3b8",
                fontWeight: tab === id ? 700 : 500,
                fontSize: 13,
                borderBottom: tab === id ? "2px solid #2563eb" : "2px solid transparent",
              }}
            >
              {label}
            </button>
          ))}
        </div>
      </div>

      {/* List */}
      <div className="flex-1 overflow-y-auto px-5 pt-4 pb-6 flex flex-col gap-3">
        {error && (
          <p className="rounded-2xl px-4 py-3" style={{ background: "#fef2f2", color: "#b91c1c", fontSize: 13, fontWeight: 700 }}>
            {error}
          </p>
        )}
        {data.length === 0 && !error && (
          <p className="rounded-2xl px-4 py-3" style={{ background: "#f8fafc", color: "#64748b", fontSize: 13, fontWeight: 700 }}>
            No hay resultados.
          </p>
        )}

        {data.map((req, i) => {
          const st = statusConfig[req.status];
          return (
            <motion.button
              key={req.id}
              initial={{ opacity: 0, y: 8 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: i * 0.06 }}
              onClick={() => onRequestPress(req)}
              className="bg-white rounded-2xl p-4 text-left w-full transition-all active:scale-[0.98]"
              style={{ border: "1px solid rgba(0,0,0,0.06)", boxShadow: "0 1px 4px rgba(0,0,0,0.04)" }}
            >
              <div className="flex items-start justify-between gap-2 mb-2">
                <p style={{ fontWeight: 700, fontSize: 14, color: "#0f172a", flex: 1, lineHeight: 1.3 }}>
                  {req.title}
                </p>
                <span
                  className="px-2.5 py-1 rounded-full shrink-0"
                  style={{ background: st.bg, color: st.color, fontSize: 11, fontWeight: 700 }}
                >
                  {st.label}
                </span>
              </div>

              <p style={{ fontSize: 12, color: "#64748b", marginBottom: 12, lineHeight: 1.5 }}>
                {req.description}
              </p>

              <div className="flex flex-wrap gap-2 mb-3">
                <Chip label={req.category} color="#0891b2" bg="#ecfeff" />
                <Chip label={req.modal} color="#7c3aed" bg="#f5f3ff" />
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="flex items-center gap-1">
                    <MapPin size={12} color="#94a3b8" strokeWidth={1.8} />
                    <span style={{ fontSize: 11, color: "#94a3b8", fontWeight: 500 }}>{req.location}</span>
                  </div>
                  <div className="flex items-center gap-1">
                    <Users size={12} color="#94a3b8" strokeWidth={1.8} />
                    <span style={{ fontSize: 11, color: "#94a3b8", fontWeight: 500 }}>
                      {req.proposals} prop.
                    </span>
                  </div>
                </div>
                <div className="flex items-center gap-1">
                  <DollarSign size={12} color="#2563eb" strokeWidth={2} />
                  <span style={{ fontSize: 12, color: "#2563eb", fontWeight: 700 }}>{req.price}</span>
                </div>
              </div>
            </motion.button>
          );
        })}
      </div>
    </div>
  );
}

function mapRequest(req: ApiRequest, viewerRole: "SOLICITANTE" | "PRESTADOR" | null): ServiceRequest {
  const description = req.descripcionNecesidad ?? "Solicitud de servicio";
  const title = description.split(".")[0] || "Solicitud de servicio";
  const locality = req.ubicacion?.localidad || req.ubicacion?.ciudad || "CABA";
  const requesterId = req.solicitanteId || "";
  const requesterName = requesterId ? `Usuario ${requesterId.slice(0, 6)}` : "Solicitante";
  return {
    id: req.id,
    viewerRole,
    title,
    description,
    category: req.categoriaServicioId ? `Categoría ${req.categoriaServicioId.slice(0, 6)}` : "Sin categoría",
    location: locality,
    proposals: 0,
    price: formatMoney(req.precioReferencia),
    date: req.createdAt ? new Date(req.createdAt).toLocaleDateString("es-AR") : "Sin fecha",
    status: toUiStatus(req.estado),
    requesterName,
    requesterInitials: requesterName
      .split(" ")
      .map((chunk) => chunk[0])
      .join("")
      .slice(0, 2)
      .toUpperCase(),
    modal: fromApiModality(req.modalidadServicio),
  };
}

function toUiStatus(status?: string): ServiceRequest["status"] {
  if (status === "CANCELADA") return "cancelled";
  if (status === "FINALIZADA") return "completed";
  if (status === "ASIGNADA" || status === "EN_CURSO") return "in-progress";
  return "open";
}

function Chip({ label, color, bg }: { label: string; color: string; bg: string }) {
  return (
    <span
      className="px-2.5 py-1 rounded-full"
      style={{ background: bg, color, fontSize: 11, fontWeight: 700 }}
    >
      {label}
    </span>
  );
}
