import React, { useState } from "react";
import http from "../api/http";
import "../css/Forgot.css";

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState("");
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setMsg("");
    setErr("");
    setLoading(true);

    try {
      // Tvoj backend endpoint je /api/auth/forgot-password
      await http.post("/auth/forgot-password", { email });
      setMsg("Ako nalog postoji, poslali smo ti link za reset lozinke na email.");
    } catch (e) {
      // Backend vraća 200 čak i kad korisnik ne postoji (iz bezbednosnih razloga)
      setMsg("Ako nalog postoji, poslali smo ti link za reset lozinke na email.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Zaboravljena lozinka</h2>
        <p className="muted">Unesi svoj email da pošaljemo link za reset lozinke.</p>

        {err && <div className="auth-alert">{err}</div>}
        {msg && <div className="auth-success">{msg}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="field">
            <label>Email adresa</label>
            <input
              type="email"
              placeholder="tvoj@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <button className="btn-primary" disabled={loading}>
            {loading ? "Slanje…" : "Pošalji link"}
          </button>
        </form>

        <div className="auth-footer">
          <a href="/login">← Nazad na prijavu</a>
        </div>
      </div>
    </div>
  );
}