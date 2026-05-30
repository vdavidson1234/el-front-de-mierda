import React, { useEffect, useState } from "react";
import { Plus, MapPin, Clock, DollarSign, Pause, Trash2, Play } from "lucide-react";
import { motion, AnimatePresence } from "motion/react";
import { formatMoney, fromApiModality, servifyApi, type ApiPublication } from "../api";

interface Publication {
  id: number | string;
  title: string;
  category: string;
  description: string;
  price: string;
  modality: string;
  location: string;
  schedule: string;
  active: boolean;
}

interface MyPublicationsProps {
  userId?: string;
  onNew: () => void;
}

export function MyPublications({ userId, onNew }: MyPublicationsProps) {
  const [pubs, setPubs] = useState<Publication[]>([]);
  const [deletingId, setDeletingId] = useState<number | null>(null);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!userId) return;
    let ignore = false;
    servifyApi
      .listUserPublications(userId)
      .then((items) => {
        if (!ignore) setPubs(items.map(mapPublication));
      })
      .catch((err) => {
        if (!ignore) setError(err instanceof Error ? err.message : "No se pudieron cargar las publicaciones");
      });
    return () => {
      ignore = true;
    };
  }, [userId]);

  const toggleActive = async (id: number | string) => {
    const current = pubs.find((p) => p.id === id);
    if (!current) return;
    setError("");
    setPubs((prev) =>
      prev.map((p) => (p.id === id ? { ...p, active: !p.active } : p))
    );
    if (userId && typeof id === "string") {
      try {
        const updated = await servifyApi.changePublicationState(id, userId, !current.active);
        setPubs((prev) => prev.map((p) => (p.id === id ? mapPublication(updated) : p)));
      } catch (err) {
        setPubs((prev) => prev.map((p) => (p.id === id ? current : p)));
        setError(err instanceof Error ? err.message : "No se pudo cambiar el estado");
      }
    }
  };

  const handleDelete = (id: number | string) => {
    if (typeof id !== "number") {
      setError("El backend no expone borrado de publicaciones; podes pausarla.");
      return;
    }
    setDeletingId(id);
    setTimeout(() => {
      setPubs((prev) => prev.filter((p) => p.id !== id));
      setDeletingId(null);
    }, 300);
  };

  const active = pubs.filter((p) => p.active).length;

  return (
    <div className="flex flex-col h-full" style={{ background: "#f8fafc" }}>
      {/* Header */}
      <div className="px-5 pt-12 pb-5 bg-white">
        <div className="flex items-center justify-between mb-1">
          <h1 style={{ fontSize: 24, fontWeight: 800, color: "#0f172a" }}>Mis publicaciones</h1>
          <button
            onClick={onNew}
            className="flex items-center gap-1.5 px-4 py-2 rounded-xl transition-all active:scale-95"
            style={{ background: "#0891b2", color: "white", fontWeight: 700, fontSize: 13 }}
          >
            <Plus size={16} strokeWidth={2.5} />
            Nuevo
          </button>
        </div>
        <p style={{ fontSize: 13, color: "#64748b", fontWeight: 500 }}>
          {pubs.length} servicios publicados · {active} activos
        </p>
      </div>

      {/* List */}
      <div className="flex-1 overflow-y-auto px-5 pt-4 pb-6 flex flex-col gap-4">
        {error && (
          <p className="rounded-2xl px-4 py-3" style={{ background: "#fef2f2", color: "#b91c1c", fontSize: 13, fontWeight: 700 }}>
            {error}
          </p>
        )}
        <AnimatePresence>
          {pubs.map((pub, i) => (
            <motion.div
              key={pub.id}
              initial={{ opacity: 0, y: 8 }}
              animate={{ opacity: deletingId === pub.id ? 0 : 1, y: 0, scale: deletingId === pub.id ? 0.95 : 1 }}
              exit={{ opacity: 0, scale: 0.95 }}
              transition={{ duration: 0.3, delay: deletingId === pub.id ? 0 : i * 0.06 }}
              className="bg-white rounded-2xl overflow-hidden"
              style={{ border: "1px solid rgba(0,0,0,0.06)", boxShadow: "0 1px 4px rgba(0,0,0,0.04)" }}
            >
              {/* Card body */}
              <div className="p-4">
                <div className="flex items-start justify-between gap-2 mb-1.5">
                  <h3 style={{ fontWeight: 700, fontSize: 15, color: "#0f172a", flex: 1, lineHeight: 1.3 }}>
                    {pub.title}
                  </h3>
                  <span
                    className="px-2.5 py-1 rounded-full shrink-0"
                    style={{
                      background: pub.active ? "#f0fdf4" : "#f1f5f9",
                      color: pub.active ? "#16a34a" : "#94a3b8",
                      fontSize: 11,
                      fontWeight: 700,
                    }}
                  >
                    {pub.active ? "Activo" : "Pausado"}
                  </span>
                </div>

                <span
                  className="inline-block px-2.5 py-1 rounded-full mb-2"
                  style={{ background: "#ecfeff", color: "#0891b2", fontSize: 11, fontWeight: 700 }}
                >
                  {pub.category}
                </span>

                <p style={{ fontSize: 13, color: "#64748b", lineHeight: 1.5, marginBottom: 12 }}>
                  {pub.description}
                </p>

                <div className="flex flex-wrap gap-3">
                  <InfoItem icon={<DollarSign size={12} color="#2563eb" strokeWidth={2} />} label={pub.price} color="#2563eb" />
                  <InfoItem icon={<MapPin size={12} color="#94a3b8" strokeWidth={1.8} />} label={pub.location} />
                  <InfoItem icon={<Clock size={12} color="#94a3b8" strokeWidth={1.8} />} label={pub.schedule} />
                </div>
              </div>

              {/* Actions */}
              <div
                className="flex items-center gap-2 px-4 py-3"
                style={{ borderTop: "1px solid #f1f5f9" }}
              >
                <button
                  onClick={() => toggleActive(pub.id)}
                  className="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-xl transition-all active:scale-95"
                  style={{
                    background: pub.active ? "#fff7ed" : "#f0fdf4",
                    color: pub.active ? "#d97706" : "#16a34a",
                    fontWeight: 700,
                    fontSize: 13,
                    border: `1.5px solid ${pub.active ? "#fed7aa" : "#bbf7d0"}`,
                  }}
                >
                  {pub.active ? (
                    <>
                      <Pause size={14} strokeWidth={2} />
                      Pausar
                    </>
                  ) : (
                    <>
                      <Play size={14} strokeWidth={2} />
                      Activar
                    </>
                  )}
                </button>
                <button
                  onClick={() => handleDelete(pub.id)}
                  className="flex items-center justify-center gap-2 px-4 py-2.5 rounded-xl transition-all active:scale-95"
                  style={{
                    background: "#fef2f2",
                    color: "#ef4444",
                    fontWeight: 700,
                    fontSize: 13,
                    border: "1.5px solid #fecaca",
                  }}
                >
                  <Trash2 size={14} strokeWidth={1.8} />
                  Eliminar
                </button>
              </div>
            </motion.div>
          ))}
        </AnimatePresence>

        {pubs.length === 0 && (
          <div className="flex flex-col items-center justify-center py-16 gap-4">
            <div
              className="flex items-center justify-center rounded-3xl"
              style={{ width: 72, height: 72, background: "#f1f5f9" }}
            >
              <span style={{ fontSize: 32 }}>📋</span>
            </div>
            <div className="text-center">
              <p style={{ fontWeight: 700, fontSize: 16, color: "#0f172a" }}>Sin publicaciones</p>
              <p style={{ fontSize: 13, color: "#94a3b8", marginTop: 4 }}>
                Publicá tu primer servicio para empezar a recibir pedidos
              </p>
            </div>
            <button
              onClick={onNew}
              className="px-6 py-3 rounded-2xl transition-all active:scale-95"
              style={{ background: "#2563eb", color: "white", fontWeight: 700, fontSize: 14 }}
            >
              Publicar servicio
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

function mapPublication(pub: ApiPublication): Publication {
  return {
    id: pub.id,
    title: pub.titulo,
    category: "Categoria Servify",
    description: pub.descripcion,
    price: formatMoney(pub.precioBase),
    modality: fromApiModality(pub.modalidadServicio),
    location: pub.ubicacion?.localidad || pub.ubicacion?.ciudad || "CABA",
    schedule: "Lun-Vie 9:00-18:00",
    active: pub.estado !== "PAUSADA" && pub.estado !== "INACTIVA",
  };
}

function InfoItem({
  icon,
  label,
  color = "#64748b",
}: {
  icon: React.ReactNode;
  label: string;
  color?: string;
}) {
  return (
    <div className="flex items-center gap-1">
      {icon}
      <span style={{ fontSize: 12, color, fontWeight: 500 }}>{label}</span>
    </div>
  );
}
