import { useState } from "react";
import { Star, X } from "lucide-react";
import { motion, AnimatePresence } from "motion/react";

interface RatingModalProps {
  providerName: string;
  onClose: () => void;
  onSubmit: (rating: number, comment: string) => void;
}

export function RatingModal({ providerName, onClose, onSubmit }: RatingModalProps) {
  const [rating, setRating] = useState(0);
  const [hovered, setHovered] = useState(0);
  const [comment, setComment] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = () => {
    if (!rating) return;
    setSubmitted(true);
    setTimeout(() => {
      onSubmit(rating, comment);
      onClose();
    }, 1500);
  };

  const displayRating = hovered || rating;
  const ratingLabels = ["", "Malo", "Regular", "Bueno", "Muy bueno", "Excelente"];

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="absolute inset-0 flex items-end justify-center z-50"
      style={{ background: "rgba(0,0,0,0.5)" }}
      onClick={onClose}
    >
      <motion.div
        initial={{ y: 300 }}
        animate={{ y: 0 }}
        exit={{ y: 300 }}
        transition={{ type: "spring", damping: 28, stiffness: 300 }}
        className="w-full bg-white rounded-t-3xl p-6 pb-10"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Handle */}
        <div
          className="mx-auto mb-5 rounded-full"
          style={{ width: 40, height: 4, background: "#e2e8f0" }}
        />

        <div className="flex items-center justify-between mb-5">
          <div>
            <p style={{ fontSize: 19, fontWeight: 800, color: "#0f172a" }}>Calificar servicio</p>
            <p style={{ fontSize: 13, color: "#64748b", marginTop: 2 }}>
              ¿Cómo fue tu experiencia con {providerName}?
            </p>
          </div>
          <button onClick={onClose}>
            <X size={22} color="#94a3b8" strokeWidth={1.8} />
          </button>
        </div>

        {!submitted ? (
          <>
            {/* Stars */}
            <div className="flex items-center justify-center gap-3 mb-3">
              {[1, 2, 3, 4, 5].map((i) => (
                <button
                  key={i}
                  onMouseEnter={() => setHovered(i)}
                  onMouseLeave={() => setHovered(0)}
                  onClick={() => setRating(i)}
                  className="transition-all"
                  style={{ transform: displayRating >= i ? "scale(1.15)" : "scale(1)" }}
                >
                  <Star
                    size={40}
                    fill={displayRating >= i ? "#f59e0b" : "none"}
                    color={displayRating >= i ? "#f59e0b" : "#e2e8f0"}
                    strokeWidth={1.5}
                  />
                </button>
              ))}
            </div>

            <p
              className="text-center mb-5"
              style={{ fontSize: 15, fontWeight: 700, color: "#f59e0b", minHeight: 24 }}
            >
              {ratingLabels[displayRating]}
            </p>

            <div>
              <p style={{ fontSize: 13, fontWeight: 700, color: "#475569", marginBottom: 8 }}>
                Comentario (opcional)
              </p>
              <textarea
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                placeholder="Contá tu experiencia..."
                rows={3}
                className="w-full rounded-2xl px-4 py-3 resize-none outline-none"
                style={{
                  border: "1.5px solid #e2e8f0",
                  background: "#f8fafc",
                  fontSize: 14,
                  color: "#0f172a",
                }}
              />
            </div>

            <button
              onClick={handleSubmit}
              className="w-full py-3.5 rounded-2xl mt-4 transition-all active:scale-95"
              style={{
                background: rating ? "#2563eb" : "#cbd5e1",
                color: "white",
                fontWeight: 700,
                fontSize: 15,
              }}
            >
              Enviar calificación
            </button>
          </>
        ) : (
          <div className="flex flex-col items-center gap-4 py-6">
            <motion.div
              initial={{ scale: 0 }}
              animate={{ scale: 1 }}
              transition={{ type: "spring", damping: 15 }}
              className="flex items-center justify-center rounded-full"
              style={{ width: 64, height: 64, background: "#f0fdf4" }}
            >
              <span style={{ fontSize: 30 }}>⭐</span>
            </motion.div>
            <div className="text-center">
              <p style={{ fontSize: 17, fontWeight: 800, color: "#0f172a" }}>¡Gracias por tu valoración!</p>
              <p style={{ fontSize: 13, color: "#64748b", marginTop: 4 }}>
                Tu opinión ayuda a mejorar la comunidad de Servify
              </p>
            </div>
          </div>
        )}
      </motion.div>
    </motion.div>
  );
}
