import React, { useState } from "react";
import http from "../api/http";

export default function Register({ onSuccess }) {
  const [form, setForm] = useState({ username: "", email: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr(""); setOk("");
    setLoading(true);
    try {
      await http.post("/auth/register", form);
      setOk("✅ Nalog je kreiran. Proverite email i kliknite na link za aktivaciju.");
      if (typeof onSuccess === "function") onSuccess();
    } catch (e) {
      setErr(e?.response?.data || "Registracija nije uspela");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap" style={{ background: "#1a1a1a", minHeight: "100vh", display: "flex", justifyContent: "center", alignItems: "center" }}>
      <div className="auth-card" style={{ background: "#111", padding: "2rem", borderRadius: "16px", width: "360px", boxShadow: "0 4px 20px rgba(0,0,0,0.5)" }}>
        <h2 style={{ color: "#ff6600", marginBottom: "0.5rem" }}>Kreiraj nalog</h2>
        <p className="muted" style={{ color: "#aaa", marginBottom: "1.5rem" }}>Pridruži se i počni da koristiš aplikaciju</p>

        {err && <div className="auth-alert" style={{ background: "#330000", color: "#ff6666", padding: "0.5rem 1rem", borderRadius: "8px", marginBottom: "1rem" }}>{err}</div>}
        {ok && <div className="auth-success" style={{ background: "#223300", color: "#66ff66", padding: "0.5rem 1rem", borderRadius: "8px", marginBottom: "1rem" }}>{ok}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="field" style={{ marginBottom: "1rem" }}>
            <label style={{ color: "#fff", display: "block", marginBottom: "0.25rem" }}>Username</label>
            <input
              type="text"
              value={form.username}
              onChange={(e) => setForm((f) => ({ ...f, username: e.target.value }))}
              required
              style={{ width: "100%", padding: "0.5rem", borderRadius: "8px", border: "1px solid #333", background: "#222", color: "#fff" }}
            />
          </div>

          <div className="field" style={{ marginBottom: "1rem" }}>
            <label style={{ color: "#fff", display: "block", marginBottom: "0.25rem" }}>Email</label>
            <input
              type="email"
              value={form.email}
              onChange={(e) => setForm((f) => ({ ...f, email: e.target.value }))}
              required
              style={{ width: "100%", padding: "0.5rem", borderRadius: "8px", border: "1px solid #333", background: "#222", color: "#fff" }}
            />
          </div>

          <div className="field" style={{ marginBottom: "1.5rem" }}>
            <label style={{ color: "#fff", display: "block", marginBottom: "0.25rem" }}>Password</label>
            <input
              type="password"
              value={form.password}
              onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
              required
              minLength={6}
              style={{ width: "100%", padding: "0.5rem", borderRadius: "8px", border: "1px solid #333", background: "#222", color: "#fff" }}
            />
          </div>

          <button
            className="btn-primary"
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
              transition: "0.3s"
            }}
          >
            {loading ? "Kreiranje…" : "Kreiraj nalog"}
          </button>
        </form>

        <div className="auth-footer" style={{ marginTop: "1rem", textAlign: "center", color: "#aaa" }}>
          <span>Već imaš nalog? </span>
          <a href="/login" style={{ color: "#ff6600", textDecoration: "none" }}>Prijavi se</a>
        </div>
      </div>
    </div>
  );
}