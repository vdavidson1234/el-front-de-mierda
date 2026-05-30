import { useState } from "react";
import { Search, ChevronRight, ArrowRight, Bell } from "lucide-react";
import { motion } from "motion/react";

const categories = [
  { id: 1, label: "Oficios", emoji: "🔧", color: "#0891b2", bg: "#f0f9ff" },
  { id: 2, label: "Clases particulares", emoji: "📚", color: "#7c3aed", bg: "#f5f3ff" },
  { id: 3, label: "Soporte técnico", emoji: "💻", color: "#2563eb", bg: "#eff6ff" },
  { id: 4, label: "Limpieza", emoji: "✨", color: "#0891b2", bg: "#ecfeff" },
  { id: 5, label: "Diseño", emoji: "🎨", color: "#db2777", bg: "#fdf2f8" },
  { id: 6, label: "Reparaciones", emoji: "🔩", color: "#d97706", bg: "#fffbeb" },
  { id: 7, label: "Fotografía", emoji: "📷", color: "#16a34a", bg: "#f0fdf4" },
  { id: 8, label: "Salud y bienestar", emoji: "💚", color: "#059669", bg: "#ecfdf5" },
  { id: 9, label: "Otro", emoji: "🌟", color: "#7c3aed", bg: "#f5f3ff" },
];

interface ExploreScreenProps {
  userName: string;
  onCreateRequest: () => void;
  onCategoryPress: (cat: string) => void;
}

import { servifyApi, type SessionUser, type ApiRequest } from "../api";
import { useEffect } from "react";

export function ExploreScreen({ user, userName, onCreateRequest, onCategoryPress }: ExploreScreenProps & { user?: SessionUser | null }) {
  const [search, setSearch] = useState("");
  const firstName = userName.split(" ")[0];
  const [remoteRequests, setRemoteRequests] = useState<ApiRequest[] | null>(null);

  useEffect(() => {
    let ignore = false;
    setRemoteRequests(null);
    if (!user || user.role !== "provider") return;

    servifyApi
      .listReceivedRequests(String(user.id))
      .then((list) => {
        if (ignore) return;
        setRemoteRequests(list || []);
      })
      .catch(() => {
        if (ignore) return;
        setRemoteRequests([]);
      });

    return () => {
      ignore = true;
    };
  }, [user]);

  const filtered = search
    ? categories.filter((c) => c.label.toLowerCase().includes(search.toLowerCase()))
    : categories;

  return (
    <div className="flex flex-col h-full" style={{ background: "#f8fafc" }}>
      {/* Header */}
      <div
        className="px-5 pt-12 pb-5"
        style={{ background: "white" }}
      >
        <div className="flex items-center justify-between mb-4">
          <div>
            <p style={{ fontSize: 13, color: "#64748b", fontWeight: 500 }}>Hola 👋</p>
            <h1 style={{ fontSize: 22, fontWeight: 800, color: "#0f172a", lineHeight: 1.2 }}>
              ¿Qué necesitás,
              <br />
              {firstName}?
            </h1>
          </div>
          <button
            className="relative flex items-center justify-center rounded-2xl"
            style={{ width: 44, height: 44, background: "#f1f5f9" }}
          >
            <Bell size={20} color="#475569" strokeWidth={1.8} />
            <div
              className="absolute top-2 right-2 rounded-full"
              style={{ width: 8, height: 8, background: "#ef4444", border: "2px solid white" }}
            />
          </button>
        </div>

        {/* Search */}
        <div
          className="flex items-center gap-3 px-4 py-3 rounded-2xl"
          style={{ background: "#f1f5f9", border: "1.5px solid #e2e8f0" }}
        >
          <Search size={18} color="#94a3b8" strokeWidth={1.8} />
          <input
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Buscar categoría…"
            className="flex-1 bg-transparent outline-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </div>
      </div>

      {/* Scrollable content */}
      <div className="flex-1 overflow-y-auto px-5 pt-4 pb-6 flex flex-col gap-5">
        {/* If provider, try to load provider-relevant requests from backend */}
        {user?.role === "provider" && remoteRequests && (
          <div className="mb-3">
            <p style={{ fontSize: 13, color: "#64748b", marginBottom: 6 }}>Recomendados para vos</p>
            <div className="flex flex-col gap-2.5">
              {remoteRequests.length === 0 && <p style={{ color: "#64748b" }}>No hay solicitudes compatibles por ahora</p>}
              {remoteRequests.map((r, i) => (
                <motion.button
                  key={r.id}
                  initial={{ opacity: 0, y: 8 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.3, delay: i * 0.05 }}
                  onClick={() => onCategoryPress(r.descripcionNecesidad ?? "")}
                  className="flex items-center gap-4 p-4 rounded-2xl bg-white text-left transition-all active:scale-[0.98]"
                  style={{ border: "1px solid rgba(0,0,0,0.06)", boxShadow: "0 1px 4px rgba(0,0,0,0.04)" }}
                >
                  <div className="flex-1">
                    <p style={{ fontWeight: 700, fontSize: 14, color: "#0f172a" }}>{(r.descripcionNecesidad || "Solicitud de servicio").split('.')[0]}</p>
                    <p style={{ fontSize: 12, color: "#94a3b8", marginTop: 6 }}>{r.descripcionNecesidad}</p>
                  </div>
                  <ChevronRight size={18} color="#cbd5e1" strokeWidth={2} />
                </motion.button>
              ))}
            </div>
          </div>
        )}
        {/* Featured card */}
        {!search && (
          <motion.div
            initial={{ opacity: 0, y: 12 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.4 }}
            className="rounded-3xl overflow-hidden relative"
            style={{
              background: "linear-gradient(135deg, #0891b2 0%, #0e7490 50%, #164e63 100%)",
              minHeight: 150,
            }}
          >
            {/* Decorative circles */}
            <div
              className="absolute -top-6 -right-6 rounded-full opacity-20"
              style={{ width: 120, height: 120, background: "white" }}
            />
            <div
              className="absolute -bottom-8 -left-4 rounded-full opacity-10"
              style={{ width: 100, height: 100, background: "white" }}
            />

            <div className="relative p-5">
              <p style={{ fontSize: 13, color: "rgba(255,255,255,0.75)", fontWeight: 500, marginBottom: 4 }}>
                ¿Buscás un experto?
              </p>
              <h2 style={{ fontSize: 19, fontWeight: 800, color: "white", lineHeight: 1.25, marginBottom: 16 }}>
                Encontrá el experto{"\n"}que necesitás
              </h2>
              <button
                onClick={onCreateRequest}
                className="flex items-center gap-2 px-4 py-2.5 rounded-xl transition-all active:scale-95"
                style={{ background: "white", color: "#0891b2", fontWeight: 700, fontSize: 13 }}
              >
                Crear solicitud
                <ArrowRight size={15} strokeWidth={2.2} />
              </button>
            </div>
          </motion.div>
        )}

        {/* Categories */}
        <div>
          <div className="flex items-center justify-between mb-3">
            <h3 style={{ fontSize: 16, fontWeight: 700, color: "#0f172a" }}>Categorías</h3>
            <span style={{ fontSize: 12, color: "#94a3b8", fontWeight: 500 }}>
              {filtered.length} disponibles
            </span>
          </div>

          <div className="flex flex-col gap-2.5">
            {filtered.map((cat, i) => (
              <motion.button
                key={cat.id}
                initial={{ opacity: 0, y: 8 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3, delay: i * 0.05 }}
                onClick={() => onCategoryPress(cat.label)}
                className="flex items-center gap-4 p-4 rounded-2xl bg-white text-left transition-all active:scale-[0.98]"
                style={{ border: "1px solid rgba(0,0,0,0.06)", boxShadow: "0 1px 4px rgba(0,0,0,0.04)" }}
              >
                <div
                  className="flex items-center justify-center rounded-xl"
                  style={{ width: 46, height: 46, background: cat.bg, flexShrink: 0 }}
                >
                  <span style={{ fontSize: 22 }}>{cat.emoji}</span>
                </div>
                <div className="flex-1">
                  <p style={{ fontWeight: 700, fontSize: 14, color: "#0f172a" }}>{cat.label}</p>
                  <p style={{ fontSize: 12, color: "#94a3b8", marginTop: 2, fontWeight: 500 }}>
                    Ver servicios disponibles
                  </p>
                </div>
                <ChevronRight size={18} color="#cbd5e1" strokeWidth={2} />
              </motion.button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
