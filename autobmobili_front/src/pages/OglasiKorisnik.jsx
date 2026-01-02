import React, { useEffect, useState } from "react";
import http from "../api/http";

export default function OglasiKorisnik() {
  const [oglasi, setOglasi] = useState([]);
  const [marke, setMarke] = useState([]);
  const [modeli, setModeli] = useState([]);
  const [selectedMarka, setSelectedMarka] = useState("");
  const [selectedModel, setSelectedModel] = useState("");
  const [minCena, setMinCena] = useState("");
  const [maxCena, setMaxCena] = useState("");
  const [minGodiste, setMinGodiste] = useState("");
  const [maxGodiste, setMaxGodiste] = useState("");
  const [selectedGorivo, setSelectedGorivo] = useState("");
  const [selectedMenjac, setSelectedMenjac] = useState("");
  const [filteredModeli, setFilteredModeli] = useState([]);
  const [modalOglas, setModalOglas] = useState(null);
  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState(""); // "success" | "error"

  useEffect(() => {
    fetchOglasi();
    fetchMarke();
    fetchModels();
  }, []);

  const fetchOglasi = async () => {
    try {
      const res = await http.get("/oglas");
      setOglasi(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchMarke = async () => {
    try {
      const res = await http.get("/marka");
      setMarke(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchModels = async () => {
    try {
      const res = await http.get("/model");
      setModeli(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const handleMarkaChange = (e) => {
    const markaId = e.target.value;
    setSelectedMarka(markaId);
    setSelectedModel("");
    const filtered = modeli.filter(m => m.marka?.id === parseInt(markaId));
    setFilteredModeli(filtered);
  };

  const handleModelChange = (e) => setSelectedModel(e.target.value);

  const handleGorivoChange = (e) => setSelectedGorivo(e.target.value);
  const handleMenjacChange = (e) => setSelectedMenjac(e.target.value);

  const handleSacuvajOglas = async (oglasId) => {
    try {
        await http.post(`/oglas/sacuvaj/${oglasId}`);
        setMessage("Oglas sačuvan!");
        setMessageType("success");
    } catch (e) {
        console.error(e);
        setMessage("Greška prilikom čuvanja oglasa.");
        setMessageType("error");
    }

    setTimeout(() => setMessage(""), 500);
};

  // filtriraj oglase po izboru korisnika
  const filteredOglasi = oglasi.filter(o => {
    if (!o.aktivan) return false;
    if (selectedMarka && o.automobil?.model?.marka?.id !== parseInt(selectedMarka)) return false;
    if (selectedModel && o.automobil?.model?.id !== parseInt(selectedModel)) return false;
    if (minCena && o.automobil?.cena < parseFloat(minCena)) return false;
    if (maxCena && o.automobil?.cena > parseFloat(maxCena)) return false;
    if (minGodiste && o.automobil?.godiste < parseInt(minGodiste)) return false;
    if (maxGodiste && o.automobil?.godiste > parseInt(maxGodiste)) return false;
    if (selectedGorivo && o.automobil?.gorivo !== selectedGorivo) return false;
    if (selectedMenjac && o.automobil?.menjac !== selectedMenjac) return false;
    return true;
  });

  return (
    <div className="container">

      {message && (
      <div style={{
        position: "fixed",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        padding: "10px 20px",
        borderRadius: "6px",
        color: "#fff",
        backgroundColor: messageType === "success" ? "green" : "red",
        boxShadow: "0 2px 8px rgba(0,0,0,0.3)",
        zIndex: 9999,
        pointerEvents: "none"

      }}>
        {message}
      </div>
    )}

      <h2>Oglasi</h2>

      {/* Filter */}
      <div className="row-gap">
        <select value={selectedMarka} onChange={handleMarkaChange}>
          <option value="">Sve marke</option>
          {marke.map(m => <option key={m.id} value={m.id}>{m.marka}</option>)}
        </select>

        <select value={selectedModel} onChange={handleModelChange}>
          <option value="">Svi modeli</option>
          {filteredModeli.map(m => <option key={m.id} value={m.id}>{m.model}</option>)}
        </select>

        <input type="number" placeholder="Min cena" value={minCena} onChange={e => setMinCena(e.target.value)} />
        <input type="number" placeholder="Max cena" value={maxCena} onChange={e => setMaxCena(e.target.value)} />

        <input type="number" placeholder="Od godine" value={minGodiste} onChange={e => setMinGodiste(e.target.value)} />
        <input type="number" placeholder="Do godine" value={maxGodiste} onChange={e => setMaxGodiste(e.target.value)} />

        <select value={selectedGorivo} onChange={handleGorivoChange}>
          <option value="">Sva goriva</option>
          <option value="BENZIN">Benzin</option>
          <option value="DIZEL">Dizel</option>
          <option value="METAN">Metan</option>
          <option value="STRUJA">Struja</option>
        </select>

        <select value={selectedMenjac} onChange={handleMenjacChange}>
          <option value="">Svi menjači</option>
          <option value="MANUELNI">Manualni</option>
          <option value="AUTOMATIK">Automatski</option>
        </select>
      </div>

      {/* Prikaz oglasa */}
      <div className="cards-container" style={{ display: "flex", flexWrap: "wrap", gap: "20px", marginTop: "20px" }}>
        {filteredOglasi.map(o => (
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
              onClick={() => setModalOglas(o)}
              style={{ padding: "6px 12px", borderRadius: "6px", cursor: "pointer" }}
            >DETALJI</button>
            <button
                onClick={() => handleSacuvajOglas(o.id)}
                style={{ padding: "6px 12px", borderRadius: "6px", cursor: "pointer", marginLeft: "10px" }}
                >
                SAČUVAJ
            </button>
          </div>
        ))}
      </div>

      {/* Modal za detalje */}
      {modalOglas && (
        <div className="modal" style={{
          position: "fixed", top: 0, left: 0, width: "100%", height: "100%",
          backgroundColor: "rgba(0,0,0,0.5)", display: "flex", justifyContent: "center", alignItems: "center"
        }}>
          <div style={{
            background: "#1a1a1a", padding: "20px", borderRadius: "8px", maxWidth: "500px", width: "100%", position: "relative",
            color: "#fff"
          }}>
            <button onClick={() => setModalOglas(null)} style={{ position: "absolute", top: "10px", right: "10px", cursor: "pointer" }}>✖</button>
            <h2>{modalOglas.naslov}</h2>
            <img src={modalOglas.automobil?.slika} alt={modalOglas.automobil?.model?.model} style={{ width: "100%", height: "200px", objectFit: "cover", borderRadius: "6px" }} />
            <p><strong>Opis:</strong> {modalOglas.opis}</p>
            <p><strong>Marka / Model:</strong> {modalOglas.automobil?.model?.marka?.marka} / {modalOglas.automobil?.model?.model}</p>
            <p><strong>Godište:</strong> {modalOglas.automobil?.godiste}</p>
            <p><strong>Kilometraža:</strong> {modalOglas.automobil?.kilometraza}</p>
            <p><strong>Snaga:</strong> {modalOglas.automobil?.snaga}</p>
            <p><strong>Kubikaža:</strong> {modalOglas.automobil?.kubikaza}</p>
            <p><strong>Gorivo:</strong> {modalOglas.automobil?.gorivo}</p>
            <p><strong>Menjač:</strong> {modalOglas.automobil?.menjac}</p>
            <p><strong>Cena:</strong> {modalOglas.automobil?.cena} €</p>
          </div>
        </div>
      )}
    </div>
  );
}