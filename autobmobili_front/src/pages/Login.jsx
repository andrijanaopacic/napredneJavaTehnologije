import React, { useState } from "react";
import http from "../api/http";

export default function Login({ onSuccess }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr("");
    setLoading(true);
    try {
      const res = await http.post("/auth/login", form);
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("me", JSON.stringify(res.data.korisnik));
      if (typeof onSuccess === "function") onSuccess(res.data.korisnik);
    } catch (e) {
      setErr(e?.response?.data?.message || "Prijava nije uspela");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div
      className="auth-wrap"
      style={{
        background: "#1a1a1a",
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        className="auth-card"
        style={{
          background: "#111",
          padding: "2rem",
          borderRadius: "16px",
          width: "360px",
          boxShadow: "0 4px 20px rgba(0,0,0,0.5)",
        }}
      >
        <h2 style={{ color: "#ff6600", marginBottom: "0.5rem" }}>Dobrodošli</h2>
        <p style={{ color: "#aaa", marginBottom: "1.5rem" }}>Prijavite se da nastavite</p>

        {err && (
          <div
            style={{
              background: "#330000",
              color: "#ff6666",
              padding: "0.5rem 1rem",
              borderRadius: "8px",
              marginBottom: "1rem",
            }}
          >
            {err}
          </div>
        )}

        <form onSubmit={handleSubmit} className="auth-form">
          <div style={{ marginBottom: "1rem" }}>
            <label style={{ color: "#fff", display: "block", marginBottom: "0.25rem" }}>Username</label>
            <input
              type="text"
              autoComplete="username"
              value={form.username}
              onChange={(e) => setForm((f) => ({ ...f, username: e.target.value }))}
              required
              style={{
                width: "100%",
                padding: "0.5rem",
                borderRadius: "8px",
                border: "1px solid #333",
                background: "#222",
                color: "#fff",
              }}
            />
          </div>

          <div style={{ marginBottom: "1.5rem" }}>
            <label style={{ color: "#fff", display: "block", marginBottom: "0.25rem" }}>Password</label>
            <input
              type="password"
              autoComplete="current-password"
              value={form.password}
              onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
              required
              style={{
                width: "100%",
                padding: "0.5rem",
                borderRadius: "8px",
                border: "1px solid #333",
                background: "#222",
                color: "#fff",
              }}
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            style={{
              width: "100%",
              padding: "0.75rem",
              borderRadius: "12px",
              background: "#ff6600",
              color: "#fff",
              fontWeight: "bold",
              border: "none",
              cursor: loading ? "not-allowed" : "pointer",
              transition: "0.3s",
            }}
          >
            {loading ? "Prijava…" : "Prijavi se"}
          </button>
        </form>

        <div style={{ marginTop: "1rem", textAlign: "center", color: "#aaa" }}>
          <a href="/forgot" style={{ color: "#ff6600", display: "block", marginBottom: "0.5rem" }}>
            Zaboravljena lozinka?
          </a>
          <span>Nemate nalog? </span>
          <a href="/register" style={{ color: "#ff6600", textDecoration: "none" }}>
            Kreirajte ga
          </a>
        </div>
      </div>
    </div>
  );
}