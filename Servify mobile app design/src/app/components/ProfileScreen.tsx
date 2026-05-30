import React, { useEffect, useMemo, useState } from "react";
import { MapPin, Mail, Briefcase, Clock, Edit3, LogOut, User, Camera, Save } from "lucide-react";
import { servifyApi, type SessionUser } from "../api";

interface ProfileScreenProps {
  user: SessionUser | null;
  onLogout: () => void;
  onLogin: () => void;
  onUserUpdated: (patch: Partial<SessionUser>) => void;
}

const roleLabel = {
  client: "Cliente",
  provider: "Prestador",
  both: "Cliente & Prestador",
};

export function ProfileScreen({ user, onLogout, onLogin, onUserUpdated }: ProfileScreenProps) {
  const [editing, setEditing] = useState(false);
  const [loadingProfile, setLoadingProfile] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [photoUrl, setPhotoUrl] = useState("");
  const [localidad, setLocalidad] = useState("CABA");
  const [availabilityFrom, setAvailabilityFrom] = useState("09:00");
  const [availabilityTo, setAvailabilityTo] = useState("18:00");

  useEffect(() => {
    if (!user) return;

    let cancelled = false;

    const loadProfile = async () => {
      setLoadingProfile(true);
      setError(null);
      setSuccess(null);

      try {
        const [accountConfig, profile] = await Promise.all([
          servifyApi.getAccountConfig(user.id).catch(() => null),
          servifyApi.getUserProfile(user.id).catch(() => null),
        ]);

        if (cancelled) return;

        const prefs = servifyApi.getProfilePreferences(user.id);

        const resolvedName = profile?.nombre || user.name.split(" ")[0] || "";
        const resolvedLastName = profile?.apellido || user.name.split(" ").slice(1).join(" ") || "";

        setFirstName(resolvedName);
        setLastName(resolvedLastName);
        setPhotoUrl(profile?.fotoPerfilUrl ?? "");
        setLocalidad(profile?.ubicacion?.localidad ?? "CABA");
        setEmail(prefs.email || accountConfig?.usuario?.email || user.email || "");
        setAvailabilityFrom(prefs.availabilityFrom || "09:00");
        setAvailabilityTo(prefs.availabilityTo || "18:00");
      } finally {
        if (!cancelled) {
          setLoadingProfile(false);
        }
      }
    };

    loadProfile();

    return () => {
      cancelled = true;
    };
  }, [user]);

  if (!user) {
    return (
      <div className="flex flex-col h-full items-center justify-center gap-5 px-8" style={{ background: "#f8fafc" }}>
        <div
          className="flex items-center justify-center rounded-3xl"
          style={{ width: 80, height: 80, background: "#eff6ff" }}
        >
          <User size={36} color="#2563eb" strokeWidth={1.6} />
        </div>
        <div className="text-center">
          <p style={{ fontSize: 20, fontWeight: 800, color: "#0f172a" }}>Sin cuenta</p>
          <p style={{ fontSize: 14, color: "#64748b", marginTop: 6, lineHeight: 1.5 }}>
            Iniciá sesión para ver tu perfil, gestionar solicitudes y publicar servicios
          </p>
        </div>
        <button
          onClick={onLogin}
          className="px-8 py-3.5 rounded-2xl transition-all active:scale-95"
          style={{ background: "#2563eb", color: "white", fontWeight: 700, fontSize: 15 }}
        >
          Ingresar
        </button>
      </div>
    );
  }

  const displayName = `${firstName} ${lastName}`.trim() || user.name;

  const initials = displayName
    .split(" ")
    .map((n) => n[0])
    .join("")
    .slice(0, 2)
    .toUpperCase();

  const availabilityLabel = `${availabilityFrom} – ${availabilityTo}`;

  const canSave = useMemo(
    () => Boolean(firstName.trim() && lastName.trim() && email.trim() && availabilityFrom && availabilityTo),
    [firstName, lastName, email, availabilityFrom, availabilityTo]
  );

  const handleSaveProfile = async () => {
    if (!user || !canSave) return;

    setSaving(true);
    setError(null);
    setSuccess(null);

    try {
      await servifyApi.updateUserProfile(user.id, {
        nombre: firstName.trim(),
        apellido: lastName.trim(),
        fotoPerfilUrl: photoUrl.trim(),
        localidad: localidad.trim() || "CABA",
        descripcionPersonal: `Disponibilidad ${availabilityFrom}-${availabilityTo}`,
      });

      servifyApi.saveProfilePreferences(user.id, {
        email: email.trim(),
        availabilityFrom,
        availabilityTo,
      });

      onUserUpdated({
        name: `${firstName.trim()} ${lastName.trim()}`.trim(),
        email: email.trim(),
      });

      setSuccess("Perfil actualizado correctamente");
      setEditing(false);
    } catch (err) {
      setError(err instanceof Error ? err.message : "No se pudo actualizar el perfil");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="flex flex-col h-full" style={{ background: "#f8fafc" }}>
      {/* Hero header */}
      <div
        className="px-5 pt-12 pb-8 relative overflow-hidden"
        style={{ background: "linear-gradient(160deg, #1d4ed8 0%, #2563eb 50%, #0891b2 100%)" }}
      >
        {/* Decorative */}
        <div
          className="absolute -top-10 -right-10 rounded-full opacity-10"
          style={{ width: 140, height: 140, background: "white" }}
        />
        <div
          className="absolute bottom-0 left-0 rounded-full opacity-10"
          style={{ width: 80, height: 80, background: "white" }}
        />

        <div className="relative flex items-start justify-between mb-5">
          <div className="flex items-center gap-4">
            {/* Avatar */}
            <div
              className="flex items-center justify-center rounded-full"
              style={{
                width: 68,
                height: 68,
                background: "rgba(255,255,255,0.2)",
                border: "3px solid rgba(255,255,255,0.5)",
              }}
            >
              {photoUrl ? (
                <img
                  src={photoUrl}
                  alt="Avatar"
                  style={{ width: "100%", height: "100%", objectFit: "cover", borderRadius: "50%" }}
                />
              ) : (
                <span style={{ fontSize: 24, fontWeight: 800, color: "white" }}>{initials}</span>
              )}
            </div>
            <div>
              <p style={{ fontSize: 19, fontWeight: 800, color: "white", lineHeight: 1.2 }}>
                {displayName}
              </p>
              <div className="flex items-center gap-1 mt-1">
                <MapPin size={12} color="rgba(255,255,255,0.75)" strokeWidth={1.8} />
                <span style={{ fontSize: 12, color: "rgba(255,255,255,0.75)", fontWeight: 500 }}>
                  {localidad || "CABA"}
                </span>
              </div>
            </div>
          </div>

          <button
            onClick={() => {
              setEditing((prev) => !prev);
              setError(null);
              setSuccess(null);
            }}
            className="flex items-center justify-center rounded-xl"
            style={{ width: 38, height: 38, background: "rgba(255,255,255,0.2)" }}
          >
            <Edit3 size={17} color="white" strokeWidth={1.8} />
          </button>
        </div>

        <div className="rounded-2xl px-4 py-3" style={{ background: "rgba(255,255,255,0.15)" }}>
          <p style={{ fontSize: 12, color: "rgba(255,255,255,0.9)", fontWeight: 600 }}>
            Las valoraciones se actualizan cuando un servicio se completa y ambas partes confirman la finalización. El
            backend registra las calificaciones y devuelve el promedio para cada usuario.
          </p>
        </div>
      </div>

      {/* Content */}
      <div className="flex-1 overflow-y-auto px-5 pt-5 pb-8 flex flex-col gap-4">
        {/* Info card */}
        <div
          className="bg-white rounded-2xl p-4"
          style={{ border: "1px solid rgba(0,0,0,0.06)", boxShadow: "0 1px 4px rgba(0,0,0,0.04)" }}
        >
          {loadingProfile ? (
            <p style={{ fontSize: 13, color: "#64748b", marginBottom: 12 }}>Cargando perfil...</p>
          ) : null}

          {error ? (
            <div className="rounded-xl px-3 py-2 mb-3" style={{ background: "#fef2f2", border: "1px solid #fecaca" }}>
              <p style={{ fontSize: 12, color: "#dc2626", fontWeight: 600 }}>{error}</p>
            </div>
          ) : null}

          {success ? (
            <div className="rounded-xl px-3 py-2 mb-3" style={{ background: "#ecfdf5", border: "1px solid #bbf7d0" }}>
              <p style={{ fontSize: 12, color: "#15803d", fontWeight: 700 }}>{success}</p>
            </div>
          ) : null}

          <p style={{ fontSize: 12, fontWeight: 700, color: "#94a3b8", letterSpacing: "0.08em", textTransform: "uppercase", marginBottom: 14 }}>
            Información
          </p>
          <div className="flex flex-col gap-3.5">
            <InfoRow
              icon={<Mail size={15} color="#0891b2" strokeWidth={1.8} />}
              label="Email"
              value={email || user.email}
            />
            <InfoRow
              icon={<MapPin size={15} color="#ef4444" strokeWidth={1.8} />}
              label="Localidad"
              value={localidad || "CABA"}
            />
            <InfoRow
              icon={<Briefcase size={15} color="#7c3aed" strokeWidth={1.8} />}
              label="Tipo de cuenta"
              value={roleLabel[user.role ?? "client"]}
            />
            <InfoRow
              icon={<Clock size={15} color="#d97706" strokeWidth={1.8} />}
              label="Días de trabajo"
              value="Lun – Sáb"
            />
            <InfoRow
              icon={<Clock size={15} color="#d97706" strokeWidth={1.8} />}
              label="Horario"
              value={availabilityLabel}
            />
          </div>

          {editing ? (
            <div className="mt-4 pt-4" style={{ borderTop: "1px solid #e2e8f0" }}>
              <p style={{ fontSize: 12, fontWeight: 800, color: "#475569", marginBottom: 10 }}>Editar perfil</p>

              <div className="grid grid-cols-2 gap-2">
                <ProfileInput label="Nombre" value={firstName} onChange={setFirstName} />
                <ProfileInput label="Apellido" value={lastName} onChange={setLastName} />
              </div>

              <div className="mt-2">
                <ProfileInput label="Email" value={email} onChange={setEmail} type="email" />
                <p style={{ fontSize: 11, color: "#94a3b8", marginTop: 4 }}>
                  Se actualiza en esta app. El backend del MVP aún no expone endpoint para cambiar email.
                </p>
              </div>

              <div className="mt-2">
                <ProfileInput label="URL de foto" value={photoUrl} onChange={setPhotoUrl} />
              </div>

              <div className="mt-2">
                <ProfileInput label="Localidad" value={localidad} onChange={setLocalidad} />
              </div>

              <div className="grid grid-cols-2 gap-2 mt-2">
                <ProfileInput label="Desde" value={availabilityFrom} onChange={setAvailabilityFrom} type="time" />
                <ProfileInput label="Hasta" value={availabilityTo} onChange={setAvailabilityTo} type="time" />
              </div>

              <button
                onClick={handleSaveProfile}
                disabled={!canSave || saving}
                className="w-full mt-3 py-3 rounded-xl flex items-center justify-center gap-2"
                style={{
                  background: canSave ? "#2563eb" : "#cbd5e1",
                  color: "white",
                  fontWeight: 700,
                }}
              >
                {saving ? <Camera size={16} strokeWidth={1.8} /> : <Save size={16} strokeWidth={1.8} />}
                {saving ? "Guardando..." : "Guardar cambios"}
              </button>
            </div>
          ) : null}
        </div>

        {/* Logout */}
        <button
          onClick={onLogout}
          className="w-full py-3.5 rounded-2xl flex items-center justify-center gap-2 mt-2 transition-all active:scale-95"
          style={{
            background: "#fef2f2",
            color: "#ef4444",
            fontWeight: 700,
            fontSize: 14,
            border: "1.5px solid #fecaca",
          }}
        >
          <LogOut size={16} strokeWidth={1.8} />
          Cerrar sesión
        </button>
      </div>
    </div>
  );
}

function ProfileInput({
  label,
  value,
  onChange,
  type = "text",
}: {
  label: string;
  value: string;
  onChange: (next: string) => void;
  type?: string;
}) {
  return (
    <label className="flex flex-col gap-1">
      <span style={{ fontSize: 11, fontWeight: 700, color: "#64748b" }}>{label}</span>
      <input
        type={type}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        className="px-3 py-2.5 rounded-xl"
        style={{ border: "1px solid #cbd5e1", fontSize: 13, color: "#0f172a", background: "white" }}
      />
    </label>
  );
}

function InfoRow({
  icon,
  label,
  value,
}: {
  icon: React.ReactNode;
  label: string;
  value: string;
}) {
  return (
    <div className="flex items-center justify-between">
      <div className="flex items-center gap-2.5">
        {icon}
        <span style={{ fontSize: 13, color: "#64748b", fontWeight: 500 }}>{label}</span>
      </div>
      <span style={{ fontSize: 13, color: "#0f172a", fontWeight: 700 }}>{value}</span>
    </div>
  );
}
