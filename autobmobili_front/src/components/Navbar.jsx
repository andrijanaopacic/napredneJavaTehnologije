import React from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/logoo.jpg";

function isAuthed() {
  return !!localStorage.getItem("token");
}

function getMe() {
  try {
    return JSON.parse(localStorage.getItem("me") || "null");
  } catch {
    return null;
  }
}

export default function Navbar() {
  const nav = useNavigate();
  const authed = isAuthed();
  const me = getMe();

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("me");
    nav("/login");
  }

  const active = ({ isActive }) => ({
    color: isActive ? "#fff" : "rgba(255,255,255,0.7)",
    fontWeight: isActive ? 700 : 500,
    textDecoration: "none"
  });

  return (
    <header
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100%",
        backgroundColor: "#ff6f00",
        color: "#fff",
        boxShadow: "0 4px 6px rgba(0,0,0,0.3)",
        zIndex: 9999
      }}
    >
      <div
        style={{
          maxWidth: "1400px",
          margin: "0 auto",
          padding: "10px 20px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          flexWrap: "wrap"
        }}
      >
        {/* Logo i naziv */}
        <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
          <img
            src={logo}
            alt="Logo"
            style={{ height: "50px", borderRadius: "8px" }}
          />
          <span style={{ fontWeight: "700", fontSize: "1.4rem" }}>EndryCar</span>
        </div>

        {/* Linkovi */}
        <nav
          style={{
            display: "flex",
            flexWrap: "wrap",
            gap: "16px",
            alignItems: "center",
            fontWeight: "600",
          }}
        >
          {authed && me ? (
            me.uloga === "ADMIN" ? (
              <>
                <NavLink to="/pocetna" style={active}>Početna</NavLink>
                <NavLink to="/marke" style={active}>Marke</NavLink>
                <NavLink to="/modeli" style={active}>Modeli</NavLink>
                <NavLink to="/automobili" style={active}>Automobili</NavLink>
                <NavLink to="/oglasi" style={active}>Oglasi</NavLink>
              </>
            ) : me.uloga === "KORISNIK" ? (
              <>
                <NavLink to="/pocetna" style={active}>Početna</NavLink>
                <NavLink to="/oglasi-korisnik" style={active}>Oglasi</NavLink> 
                <NavLink to="/sacuvani-oglasi" style={active}>Sačuvani</NavLink>
              </>
            ) : null
          ) : (
            // Nije prijavljen
            <>
              <NavLink to="/login" style={active}>Prijava</NavLink>
              <NavLink to="/register" style={active}>Registracija</NavLink>
            </>
          )}
        </nav>




        {/* Desni deo - korisnik ili dugmad */}
        <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
          {authed ? (
            <>
              <span style={{
                background: "rgba(255,255,255,0.2)",
                padding: "6px 12px",
                borderRadius: "20px",
                fontWeight: "600"
              }}>
                {me?.username ?? "Korisnik"}
              </span>
              <button
                onClick={handleLogout}
                style={{
                  background: "transparent",
                  border: "2px solid white",
                  color: "white",
                  padding: "5px 12px",
                  borderRadius: "6px",
                  cursor: "pointer"
                }}
              >
                Odjava
              </button>
            </>
          ) : null}
        </div>
      </div>
    </header>
  );
}