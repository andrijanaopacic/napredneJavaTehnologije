import React, { useEffect, useState } from "react";
import http from "../api/http";

export default function SacuvaniOglasi() {
  const [sacuvaniOglasi, setSacuvaniOglasi] = useState([]);

  useEffect(() => {
    fetchSacuvane();
  }, []);

  const fetchSacuvane = async () => {
    try {
        const res = await http.get("/oglas/sacuvani"); // GET sacuvanih oglasa
        setSacuvaniOglasi(res.data);
    } catch (e) {
        console.error(e);
    }
    };

    const handleRemove = async (id) => {
    try {
        await http.delete(`/oglas/sacuvani/${id}`);
        setSacuvaniOglasi(prev => prev.filter(o => o.id !== id));
    } catch (e) {
        console.error(e);
    }
    };

  return (
    <div className="container">
      <h2>Sačuvani oglasi</h2>
      <div className="cards-container" style={{ display: "flex", flexWrap: "wrap", gap: "20px", marginTop: "20px" }}>
        {sacuvaniOglasi.map(o => (
          <div key={o.id} className="card" style={{
            border: "1px solid #ccc",
            borderRadius: "8px",
            width: "calc(33.333% - 20px)",
            boxSizing: "border-box",
            padding: "10px",
            textAlign: "center"
          }}>
            <img
              src={o.automobil?.slika || "https://via.placeholder.com/200"}
              alt={o.automobil?.model?.model}
              style={{ width: "100%", height: "150px", objectFit: "cover", borderRadius: "6px" }}
            />
            <h3>{o.naslov}</h3>
            <p>{o.automobil?.model?.marka?.marka} / {o.automobil?.model?.model}</p>
            <p>Godište: {o.automobil?.godiste}</p>
            <p>Cena: {o.automobil?.cena} €</p>
            <button
              onClick={() => handleRemove(o.id)}
              style={{ padding: "6px 12px", borderRadius: "6px", cursor: "pointer", marginTop: "10px" }}
            >
              Ukloni
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}