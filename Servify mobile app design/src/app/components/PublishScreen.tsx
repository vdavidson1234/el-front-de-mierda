import React, { useState } from "react";
import { motion } from "motion/react";
import { FileText, AlignLeft, MapPin, DollarSign, Clock, CheckCircle } from "lucide-react";
import { servifyApi } from "../api";

const categories = [
  "Oficios", "Clases particulares", "Soporte técnico", "Limpieza",
  "Diseño", "Reparaciones", "Fotografía", "Salud y bienestar", "Otro",
];
const modalities = ["Presencial", "Virtual", "Ambas"];

interface PublishScreenProps {
  userId?: string;
  onPublished: () => void;
}

export function PublishScreen({ userId, onPublished }: PublishScreenProps) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [selectedModality, setSelectedModality] = useState<string | null>(null);
  const [price, setPrice] = useState("");
  const [neighborhood, setNeighborhood] = useState("");
  const [address, setAddress] = useState("");
  const [availability, setAvailability] = useState("");
  const [published, setPublished] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const canPublish = Boolean(userId && title && description && selectedCategory && selectedModality && price);

  const handlePublish = async () => {
    if (!canPublish) return;
    setLoading(true);
    setError("");
    try {
      await servifyApi.createPublication({
        usuarioId: userId!,
        categoria: selectedCategory!,
        titulo: title,
        descripcion: description,
        modalidad: selectedModality!,
        localidad: neighborhood,
        direccion: address,
        precio: price,
      });
      setPublished(true);
      setTimeout(() => {
        setPublished(false);
        onPublished();
      }, 1200);
    } catch (err) {
      setError(err instanceof Error ? err.message : "No se pudo publicar el servicio");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col h-full" style={{ background: "#f8fafc" }}>
      {/* Header */}
      <div className="px-5 pt-12 pb-5 bg-white">
        <h1 style={{ fontSize: 24, fontWeight: 800, color: "#0f172a" }}>Publicar servicio</h1>
        <p style={{ fontSize: 14, color: "#64748b", marginTop: 3, fontWeight: 500 }}>
          Ofrecé tus habilidades a la comunidad
        </p>
      </div>

      {/* Form */}
      <div className="flex-1 overflow-y-auto px-5 pt-5 pb-8 flex flex-col gap-5">
        {!userId && (
          <p className="rounded-2xl px-4 py-3" style={{ background: "#fef2f2", color: "#b91c1c", fontSize: 13, fontWeight: 700 }}>
            Inicia sesion para publicar servicios.
          </p>
        )}
        {error && (
          <p className="rounded-2xl px-4 py-3" style={{ background: "#fef2f2", color: "#b91c1c", fontSize: 13, fontWeight: 700 }}>
            {error}
          </p>
        )}
        <FormField label="Título del servicio" icon={<FileText size={16} color="#0891b2" strokeWidth={1.8} />}>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Ej: Plomería y reparaciones generales"
            className="w-full bg-transparent outline-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </FormField>

        <FormField label="Descripción" icon={<AlignLeft size={16} color="#0891b2" strokeWidth={1.8} />}>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Describí tu servicio, experiencia y lo que incluye..."
            rows={3}
            className="w-full bg-transparent outline-none resize-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </FormField>

        {/* Category chips */}
        <div>
          <p style={{ fontSize: 13, fontWeight: 700, color: "#475569", marginBottom: 10 }}>
            Categoría
          </p>
          <div className="flex flex-wrap gap-2">
            {categories.map((cat) => {
              const sel = selectedCategory === cat;
              return (
                <button
                  key={cat}
                  onClick={() => setSelectedCategory(sel ? null : cat)}
                  className="px-3.5 py-2 rounded-full transition-all"
                  style={{
                    background: sel ? "#0891b2" : "#f1f5f9",
                    color: sel ? "white" : "#475569",
                    fontWeight: sel ? 700 : 500,
                    fontSize: 13,
                    border: sel ? "none" : "1.5px solid #e2e8f0",
                  }}
                >
                  {cat}
                </button>
              );
            })}
          </div>
        </div>

        {/* Modality chips */}
        <div>
          <p style={{ fontSize: 13, fontWeight: 700, color: "#475569", marginBottom: 10 }}>
            Modalidad
          </p>
          <div className="flex gap-2">
            {modalities.map((mod) => {
              const sel = selectedModality === mod;
              return (
                <button
                  key={mod}
                  onClick={() => setSelectedModality(sel ? null : mod)}
                  className="px-4 py-2 rounded-full transition-all"
                  style={{
                    background: sel ? "#2563eb" : "#f1f5f9",
                    color: sel ? "white" : "#475569",
                    fontWeight: sel ? 700 : 500,
                    fontSize: 13,
                    border: sel ? "none" : "1.5px solid #e2e8f0",
                  }}
                >
                  {mod}
                </button>
              );
            })}
          </div>
        </div>

        <FormField label="Precio base (ARS)" icon={<DollarSign size={16} color="#2563eb" strokeWidth={1.8} />}>
          <input
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            placeholder="Ej: 8.000"
            type="number"
            className="w-full bg-transparent outline-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </FormField>

        <div className="flex gap-3">
          <div className="flex-1">
            <FormField label="Barrio / Ciudad" icon={<MapPin size={16} color="#ef4444" strokeWidth={1.8} />}>
              <input
                value={neighborhood}
                onChange={(e) => setNeighborhood(e.target.value)}
                placeholder="Ej: Palermo"
                className="w-full bg-transparent outline-none"
                style={{ fontSize: 14, color: "#0f172a" }}
              />
            </FormField>
          </div>
        </div>

        <FormField label="Dirección exacta (opcional)" icon={<MapPin size={16} color="#94a3b8" strokeWidth={1.8} />}>
          <input
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            placeholder="Ej: Av. Santa Fe 1234, Piso 3"
            className="w-full bg-transparent outline-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </FormField>

        <FormField label="Disponibilidad" icon={<Clock size={16} color="#7c3aed" strokeWidth={1.8} />}>
          <input
            value={availability}
            onChange={(e) => setAvailability(e.target.value)}
            placeholder="Ej: Lun–Vie 9:00–18:00"
            className="w-full bg-transparent outline-none"
            style={{ fontSize: 14, color: "#0f172a" }}
          />
        </FormField>

        {/* Publish button */}
        <motion.button
          whileTap={canPublish ? { scale: 0.97 } : {}}
          onClick={handlePublish}
          className="w-full py-4 rounded-2xl mt-2 flex items-center justify-center gap-2 transition-all"
          style={{
            background: canPublish ? "#2563eb" : "#cbd5e1",
            color: "white",
            fontWeight: 700,
            fontSize: 16,
          }}
        >
          {published ? (
            <>
              <CheckCircle size={20} strokeWidth={2} />
              ¡Servicio publicado!
            </>
          ) : (
            loading ? "Publicando..." : "Publicar servicio"
          )}
        </motion.button>
      </div>
    </div>
  );
}

function FormField({
  label,
  icon,
  children,
}: {
  label: string;
  icon: React.ReactNode;
  children: React.ReactNode;
}) {
  return (
    <div>
      <p style={{ fontSize: 13, fontWeight: 700, color: "#475569", marginBottom: 8 }}>{label}</p>
      <div
        className="flex items-start gap-3 px-4 py-3.5 rounded-2xl bg-white"
        style={{ border: "1.5px solid #e2e8f0" }}
      >
        <div className="mt-0.5">{icon}</div>
        <div className="flex-1">{children}</div>
      </div>
    </div>
  );
}
