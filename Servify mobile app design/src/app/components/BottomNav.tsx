import { Compass, FileText, Briefcase, PlusSquare, User } from "lucide-react";

type Tab = "explore" | "requests" | "my-services" | "publish" | "profile";

interface BottomNavProps {
  activeTab: Tab;
  onTabChange: (tab: Tab) => void;
}

const tabs = [
  { id: "explore" as Tab, label: "Explorar", icon: Compass },
  { id: "requests" as Tab, label: "Solicitudes", icon: FileText },
  { id: "my-services" as Tab, label: "Mis servicios", icon: Briefcase },
  { id: "publish" as Tab, label: "Publicar", icon: PlusSquare },
  { id: "profile" as Tab, label: "Perfil", icon: User },
];

export function BottomNav({ activeTab, onTabChange }: BottomNavProps) {
  return (
    <div className="flex items-center justify-around bg-white border-t border-gray-100 px-2 py-2 safe-area-bottom">
      {tabs.map(({ id, label, icon: Icon }) => {
        const active = activeTab === id;
        return (
          <button
            key={id}
            onClick={() => onTabChange(id)}
            className="flex flex-col items-center gap-0.5 px-3 py-1 rounded-xl transition-all"
            style={{ minWidth: 52 }}
          >
            <Icon
              size={22}
              strokeWidth={active ? 2.2 : 1.7}
              style={{ color: active ? "#2563eb" : "#94a3b8" }}
            />
            <span
              style={{
                fontSize: 10,
                fontWeight: active ? 700 : 500,
                color: active ? "#2563eb" : "#94a3b8",
                letterSpacing: "0.01em",
              }}
            >
              {label}
            </span>
          </button>
        );
      })}
    </div>
  );
}
