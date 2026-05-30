import React, { useState } from "react";
import { motion, AnimatePresence } from "motion/react";
import {
  Mail,
  Lock,
  User,
  MapPin,
  Calendar,
  Camera,
  Eye,
  EyeOff,
} from "lucide-react";
import { servifyApi, type RoleType, type SessionUser } from "../api";
import servifyLogo from "../../imports/servify-logo.jpeg";

type AuthTab = "login" | "register";

interface AuthScreenProps {
  onAuth: (user: SessionUser) => void;
}

const roleOptions: { id: RoleType; label: string; sub: string; emoji: string; color: string; bg: string; border: string }[] = [
  { id: "client", label: "Soy cliente", sub: "Busco y contrato servicios", emoji: "🔎", color: "#2563eb", bg: "#eff6ff", border: "#2563eb" },
  { id: "provider", label: "Soy prestador", sub: "Ofrezco servicios profesionales", emoji: "🛠️", color: "#0f766e", bg: "#ecfeff", border: "#0d9488" },
  { id: "both", label: "Ambos roles", sub: "Ofrezco y también contrato", emoji: "⚡", color: "#7c3aed", bg: "#f5f3ff", border: "#7c3aed" },
];

export function AuthScreen({ onAuth }: AuthScreenProps) {
  const [tab, setTab] = useState<AuthTab>("login");
  const [showPass, setShowPass] = useState(false);
  const [selectedRole, setSelectedRole] = useState<RoleType>(null);
  const [loginEmail, setLoginEmail] = useState("");
  const [loginPass, setLoginPass] = useState("");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [registerEmail, setRegisterEmail] = useState("");
  const [registerPass, setRegisterPass] = useState("");
  const [locality, setLocality] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    setError("");
    setLoading(true);
    try {
      onAuth(await servifyApi.login(loginEmail, loginPass));
    } catch (err) {
      setError(err instanceof Error ? err.message : "No se pudo iniciar sesion");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async () => {
    if (!selectedRole || !registerEmail || !registerPass) return;
    setError("");
    setLoading(true);
    try {
      onAuth(
        await servifyApi.register({
          nombre: name,
          apellido: lastName,
          email: registerEmail,
          password: registerPass,
          localidad: locality,
          role: selectedRole,
        })
      );
    } catch (err) {
      setError(err instanceof Error ? err.message : "No se pudo crear la cuenta");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col h-full bg-white">
      <div className="flex flex-col items-start pt-10 pb-4 px-6" style={{ background: "#ffffff" }}>
        <div className="flex items-center gap-3 mb-1">
          <div className="flex items-center justify-center rounded-2xl" style={{ width: 42, height: 42, background: "#f8fafc", border: "1px solid #e2e8f0" }}>
            <img src={servifyLogo} alt="Servify" style={{ width: 28, height: 28, objectFit: "cover", objectPosition: "center top", borderRadius: 8 }} />
          </div>
          <span style={{ fontSize: 22, fontWeight: 800, color: "#0f172a" }}>Servify</span>
        </div>
        <p style={{ fontSize: 13, color: "#64748b", fontWeight: 500, marginLeft: 55 }}>
          Encontrá el servicio que necesitás
        </p>

        <div className="flex mt-5 rounded-2xl p-1" style={{ background: "#eef2ff", width: "100%" }}>
          {(["login", "register"] as AuthTab[]).map((t) => (
            <button
              key={t}
              onClick={() => {
                setTab(t);
                setError("");
              }}
              className="flex-1 py-2 rounded-lg transition-all"
              style={{
                background: tab === t ? "white" : "transparent",
                color: tab === t ? "#2563eb" : "#64748b",
                fontWeight: 700,
                fontSize: 14,
                boxShadow: tab === t ? "0 1px 3px rgba(15,23,42,0.08)" : "none",
              }}
            >
              {t === "login" ? "Iniciar sesion" : "Registrarse"}
            </button>
          ))}
        </div>
      </div>

      <div className="flex-1 overflow-y-auto px-6 pt-6 pb-8">
        <AnimatePresence mode="wait">
          {tab === "login" ? (
            <motion.div
              key="login"
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: 20 }}
              transition={{ duration: 0.22 }}
              className="flex flex-col gap-4"
            >
              <div>
                <p style={{ fontSize: 22, fontWeight: 800, color: "#0f172a" }}>Bienvenido de nuevo</p>
                <p style={{ fontSize: 14, color: "#64748b", marginTop: 2 }}>
                  Ingresa a tu cuenta de Servify
                </p>
              </div>

              {error && <ErrorMessage message={error} />}

              <InputField
                icon={<Mail size={17} color="#94a3b8" strokeWidth={1.8} />}
                placeholder="Email"
                value={loginEmail}
                onChange={setLoginEmail}
                type="email"
              />
              <InputField
                icon={<Lock size={17} color="#94a3b8" strokeWidth={1.8} />}
                placeholder="Contraseña"
                value={loginPass}
                onChange={setLoginPass}
                type={showPass ? "text" : "password"}
                suffix={
                  <button onClick={() => setShowPass(!showPass)}>
                    {showPass ? (
                      <EyeOff size={17} color="#94a3b8" strokeWidth={1.8} />
                    ) : (
                      <Eye size={17} color="#94a3b8" strokeWidth={1.8} />
                    )}
                  </button>
                }
              />

              <button
                onClick={handleLogin}
                disabled={loading}
                className="w-full py-3.5 rounded-2xl mt-2 transition-all active:scale-95"
                style={{ background: "#2563eb", color: "white", fontWeight: 700, fontSize: 15 }}
              >
                {loading ? "Conectando..." : "Iniciar sesion"}
              </button>
            </motion.div>
          ) : (
            <motion.div
              key="register"
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -20 }}
              transition={{ duration: 0.22 }}
              className="flex flex-col gap-4"
            >
              <div>
                <p style={{ fontSize: 22, fontWeight: 800, color: "#0f172a" }}>Crear cuenta</p>
                <p style={{ fontSize: 14, color: "#64748b", marginTop: 2 }}>
                  Como vas a usar Servify?
                </p>
              </div>

              {error && <ErrorMessage message={error} />}

              <div className="grid grid-cols-3 gap-2">
                {roleOptions.map(({ id, label, sub, emoji, color, bg, border }) => {
                  const selected = selectedRole === id;
                  return (
                    <button
                      key={id}
                      onClick={() => setSelectedRole(id)}
                      className="flex flex-col items-center justify-center gap-1.5 p-3 rounded-2xl text-center transition-all"
                      style={{
                        border: selected ? `2px solid ${border}` : "1.5px solid #e2e8f0",
                        background: selected ? bg : "white",
                        minHeight: 122,
                      }}
                    >
                      <div style={{ fontSize: 29, lineHeight: 1 }}>{emoji}</div>
                      <p style={{ fontWeight: 700, fontSize: 15, color: selected ? color : "#0f172a", lineHeight: 1.2 }}>{label}</p>
                      <p style={{ fontSize: 11, color: "#64748b", lineHeight: 1.3 }}>{sub}</p>
                    </button>
                  );
                })}
              </div>

              <div className="flex flex-col items-center gap-2 py-2">
                <div
                  className="flex items-center justify-center rounded-full relative"
                  style={{ width: 76, height: 76, background: "#f1f5f9", border: "2px dashed #cbd5e1" }}
                >
                  <Camera size={22} color="#94a3b8" strokeWidth={1.7} />
                </div>
                <span style={{ fontSize: 12, color: "#94a3b8", fontWeight: 500 }}>
                  Foto de perfil (opcional)
                </span>
              </div>

              <div className="flex gap-3">
                <InputField icon={<User size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Nombre" value={name} onChange={setName} />
                <InputField icon={<User size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Apellido" value={lastName} onChange={setLastName} />
              </div>
              <InputField icon={<Mail size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Email" value={registerEmail} onChange={setRegisterEmail} type="email" />
              <InputField icon={<Lock size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Contraseña" value={registerPass} onChange={setRegisterPass} type="password" />
              <InputField icon={<MapPin size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Localidad" value={locality} onChange={setLocality} />
              <InputField icon={<Calendar size={17} color="#94a3b8" strokeWidth={1.8} />} placeholder="Fecha de nacimiento" type="date" />

              <button
                onClick={handleRegister}
                disabled={loading || !selectedRole || !registerEmail || !registerPass}
                className="w-full py-3.5 rounded-2xl mt-1 transition-all active:scale-95"
                style={{
                  background: selectedRole && registerEmail && registerPass ? "#2563eb" : "#cbd5e1",
                  color: "white",
                  fontWeight: 700,
                  fontSize: 15,
                }}
              >
                {loading ? "Creando cuenta..." : selectedRole ? "Crear cuenta" : "Selecciona tu rol"}
              </button>
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </div>
  );
}

function ErrorMessage({ message }: { message: string }) {
  return (
    <div className="rounded-2xl px-4 py-3" style={{ background: "#fef2f2", border: "1px solid #fecaca" }}>
      <p style={{ color: "#b91c1c", fontSize: 13, fontWeight: 600 }}>{message}</p>
    </div>
  );
}

function InputField({
  icon,
  placeholder,
  value,
  onChange,
  type = "text",
  suffix,
}: {
  icon?: React.ReactNode;
  placeholder: string;
  value?: string;
  onChange?: (v: string) => void;
  type?: string;
  suffix?: React.ReactNode;
}) {
  return (
    <div className="flex items-center gap-3 px-4 py-3.5 rounded-2xl" style={{ background: "#f8fafc", border: "1.5px solid #e2e8f0" }}>
      {icon}
      <input
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange?.(e.target.value)}
        className="flex-1 bg-transparent outline-none min-w-0"
        style={{ fontSize: 14, color: "#0f172a" }}
      />
      {suffix}
    </div>
  );
}
