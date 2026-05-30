import { useEffect } from "react";
import { motion } from "motion/react";
import servifyLogo from "../../imports/servify-logo.jpeg";

interface SplashScreenProps {
  onDone: () => void;
}

export function SplashScreen({ onDone }: SplashScreenProps) {
  useEffect(() => {
    const t = setTimeout(onDone, 2400);
    return () => clearTimeout(t);
  }, [onDone]);

  return (
    <div
      className="relative flex flex-col items-center justify-center h-full overflow-hidden"
      style={{
        background:
          "radial-gradient(circle at top, rgba(255,255,255,0.95) 0%, rgba(240,253,244,0.95) 18%, rgba(244,250,255,0.94) 48%, #eef6ff 100%)",
      }}
    >
      <motion.div
        initial={{ scale: 0.86, opacity: 0, y: 10 }}
        animate={{ scale: 1, opacity: 1, y: 0 }}
        transition={{ duration: 0.55, ease: [0.16, 1, 0.3, 1] }}
        className="relative z-10 flex flex-col items-center gap-6"
      >
        <div className="flex items-center gap-4" style={{ paddingInline: 10 }}>
          <motion.div
            initial={{ scale: 0.92, opacity: 0.2 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ duration: 0.8, repeat: Infinity, repeatType: "reverse", ease: "easeInOut" }}
            className="relative rounded-full"
            style={{
              width: 78,
              height: 78,
              padding: 8,
              background: "rgba(255,255,255,0.74)",
              boxShadow: "0 18px 40px rgba(15,23,42,0.10)",
            }}
          >
            <img
              src={servifyLogo}
              alt="Servify"
              className="relative z-10"
              style={{ width: "100%", height: "100%", objectFit: "cover", objectPosition: "center top", borderRadius: 20 }}
            />
          </motion.div>

          <div className="flex flex-col items-start gap-1">
            <span
              style={{
                fontSize: 42,
                fontWeight: 900,
                color: "#0f172a",
                letterSpacing: "-0.05em",
                lineHeight: 1,
              }}
            >
              Servify
            </span>
          </div>
        </div>
      </motion.div>

      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 1.1, duration: 0.6 }}
        className="absolute bottom-16 flex gap-1.5 z-10"
      >
        {[0, 1, 2].map((i) => (
          <motion.div
            key={i}
            animate={{ opacity: [0.35, 1, 0.35], y: [0, -2, 0] }}
            transition={{ duration: 1.15, repeat: Infinity, delay: i * 0.2 }}
            style={{
              width: 7,
              height: 7,
              borderRadius: "50%",
              background: i === 1 ? "#0ea5e9" : "#34d399",
            }}
          />
        ))}
      </motion.div>
    </div>
  );
}
