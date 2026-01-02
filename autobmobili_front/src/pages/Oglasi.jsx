import React, { useEffect, useState } from "react";
import http from "../api/http";
import "../css/Oglasi.css";

function getMe() {
  try {
    return JSON.parse(localStorage.getItem("me") || "null");
  } catch {
    return null;
  }
}

export default function Oglasi() {
  const me = getMe(); // trenutno ulogovani korisnik
  const [oglasi, setOglasi] = useState([]);
  const [marke, setMarke] = useState([]);
  const [modeli, setModeli] = useState([]);
  const [selectedMarka, setSelectedMarka] = useState("");
  const [selectedModel, setSelectedModel] = useState("");
  const [noviAuto, setNoviAuto] = useState({
    cena: "",
    godiste: "",
    kilometraza: "",
    snaga: "",
    kubikaza: "",
    gorivo: "",
    menjac: "",
    slika: ""
  });
  const [naslov, setNaslov] = useState("");
  const [opis, setOpis] = useState("");
  const [message, setMessage] = useState("");
  const [editingOglasId, setEditingOglasId] = useState(null);
  const [filteredModeli, setFilteredModeli] = useState([]);

  // --- fetch podaci ---
  useEffect(() => {
    fetchOglasi();
    fetchMarke();
    fetchModels();
  }, []);

  const fetchOglasi = async () => {
    try {
      const res = await http.get("/oglas");
      // filtriraj samo oglase koje je admin dodao
      if (me?.uloga === "ADMIN") {
        setOglasi(res.data.filter(o => o.korisnik?.id === me.id));
      } else {
        setOglasi(res.data);
      }
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

  // --- handleri ---
  const handleMarkaChange = (e) => {
    const markaId = e.target.value;
    setSelectedMarka(markaId);
    setSelectedModel("");

    const filtered = modeli.filter(m => m.marka?.id === parseInt(markaId));
    setFilteredModeli(filtered);
  };

  const handleAutoChange = (e) => {
    setNoviAuto({ ...noviAuto, [e.target.name]: e.target.value });
  };

  const handleSaveOglas = async () => {
    if (!naslov || !selectedMarka || !selectedModel) {
      setMessage("Popunite sve obavezne podatke!");
      return;
    }

    try {
      const payload = {
        naslov,
        opis,
        aktivan: true,
        ukupnaCena: noviAuto.cena,
        vremeOglasavanja: new Date().toISOString(),
        automobil: {
          model: { id: selectedModel },
          cena: noviAuto.cena,
          godiste: noviAuto.godiste,
          kilometraza: noviAuto.kilometraza,
          snaga: noviAuto.snaga,
          kubikaza: noviAuto.kubikaza,
          gorivo: noviAuto.gorivo,
          menjac: noviAuto.menjac,
          slika: noviAuto.slika
        },
        korisnik: { id: me.id } // vezivanje za trenutnog korisnika
      };

      if (editingOglasId) {
        await http.put(`/oglas/${editingOglasId}`, payload);
        setMessage("Oglas uspešno izmenjen!");
      } else {
        await http.post("/oglas", payload);
        setMessage("Oglas uspešno dodat!");
      }

      // reset forme
      setEditingOglasId(null);
      setNaslov("");
      setOpis("");
      setSelectedMarka("");
      setSelectedModel("");
      setFilteredModeli([]);
      setNoviAuto({
        cena: "",
        godiste: "",
        kilometraza: "",
        snaga: "",
        kubikaza: "",
        gorivo: "",
        menjac: "",
        slika: ""
      });
      fetchOglasi();
    } catch (e) {
      console.error(e);
      setMessage(e?.response?.data?.message || "Greška prilikom čuvanja oglasa.");
    }
  };

  const handleEdit = (oglas) => {
    // provera da li korisnik može da menja oglas
    if (String(oglas.korisnik?.id) !== String(me?.id)) {
      setMessage("Ne možete menjati tuđe oglase!");
      return;
    }

    setEditingOglasId(oglas.id);
    setNaslov(oglas.naslov);
    setOpis(oglas.opis);
    const markaId = oglas.automobil?.model?.marka?.id || "";
    setSelectedMarka(markaId);
    setSelectedModel(oglas.automobil?.model?.id || "");
    setNoviAuto({
      cena: oglas.automobil?.cena || "",
      godiste: oglas.automobil?.godiste || "",
      kilometraza: oglas.automobil?.kilometraza || "",
      snaga: oglas.automobil?.snaga || "",
      kubikaza: oglas.automobil?.kubikaza || "",
      gorivo: oglas.automobil?.gorivo || "",
      menjac: oglas.automobil?.menjac || "",
      slika: oglas.automobil?.slika || ""
    });

    const filtered = modeli.filter(m => m.marka?.id === markaId);
    setFilteredModeli(filtered);
  };

  const handleDelete = async (oglas) => {
    // provera da li korisnik može da briše oglas
    if (String(oglas.korisnik?.id) !== String(me?.id)) {
      setMessage("Ne možete brisati tuđe oglase!");
      return;
    }

    try {
      await http.delete(`/oglas/${oglas.id}`);
      setMessage("Oglas obrisan.");
      fetchOglasi();
    } catch (e) {
      console.error(e);
      setMessage("Greška prilikom brisanja oglasa.");
    }
  };

  const toggleAktivan = async (oglas) => {
  try {
    const updated = { ...oglas, aktivan: !oglas.aktivan };

    await http.put(`/oglas/${oglas.id}`, updated);

    setOglasi(prev =>
      prev.map(o => (o.id === oglas.id ? { ...o, aktivan: updated.aktivan } : o))
    );

    setMessage(`Oglas je sada ${updated.aktivan ? "aktivan" : "neaktivan"}.`);
  } catch (e) {
    console.error(e);
    setMessage("Greška prilikom promene statusa oglasa.");
  }
};

  // --- render ---
  return (
    <div className="container">
      <h2>Oglasi</h2>
      {message && <p className="message">{message}</p>}

      <div className="row-gap">
        <input placeholder="Naslov" value={naslov} onChange={(e) => setNaslov(e.target.value)} />
        <input placeholder="Opis" value={opis} onChange={(e) => setOpis(e.target.value)} />

        <select value={selectedMarka} onChange={handleMarkaChange}>
          <option value="">Izaberite marku</option>
          {marke.map(m => <option key={m.id} value={m.id}>{m.marka}</option>)}
        </select>

        <select value={selectedModel} onChange={(e) => setSelectedModel(e.target.value)}>
          <option value="">Izaberite model</option>
          {filteredModeli.map(m => <option key={m.id} value={m.id}>{m.model}</option>)}
        </select>

        {/* Polja za novi automobil */}
        <input name="cena" placeholder="Cena" value={noviAuto.cena} onChange={handleAutoChange} />
        <input name="godiste" placeholder="Godište" value={noviAuto.godiste} onChange={handleAutoChange} />
        <input name="kilometraza" placeholder="Kilometraža" value={noviAuto.kilometraza} onChange={handleAutoChange} />
        <input name="snaga" placeholder="Snaga" value={noviAuto.snaga} onChange={handleAutoChange} />
        <input name="kubikaza" placeholder="Kubikaža" value={noviAuto.kubikaza} onChange={handleAutoChange} />
        <input name="gorivo" placeholder="Gorivo" value={noviAuto.gorivo} onChange={handleAutoChange} />
        <input name="menjac" placeholder="Menjač" value={noviAuto.menjac} onChange={handleAutoChange} />
        <input name="slika" placeholder="Slika URL" value={noviAuto.slika} onChange={handleAutoChange} />

        <button onClick={handleSaveOglas}>{editingOglasId ? "Sačuvaj izmene" : "Dodaj Oglas"}</button>
      </div>

      <h3>Lista oglasa</h3>
      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>Naslov</th>
            <th>Opis</th>
            <th>Cena</th>
            <th>Godište</th>
            <th>Marka / Model</th>
            {me?.uloga === "ADMIN" && <th>Aktivan</th>}
            <th>Akcije</th>
          </tr>
        </thead>
        <tbody>
          {oglasi.map(o => (
            <tr key={o.id}>
              <td>{o.naslov}</td>
              <td>{o.opis}</td>
              <td>{o.automobil?.cena}</td>
              <td>{o.automobil?.godiste}</td>
              <td>{o.automobil?.model?.marka?.marka} / {o.automobil?.model?.model}</td>

              {me?.uloga === "ADMIN" && (
                <td>
                  <input
                    type="checkbox"
                    checked={o.aktivan}
                    onChange={() => toggleAktivan(o)}
                  />
                </td>
              )}

              <td>
                <button onClick={() => handleEdit(o)}>Izmeni</button>
                <button onClick={() => handleDelete(o)}>Obriši</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}