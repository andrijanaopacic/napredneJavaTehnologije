import React, { useEffect, useState } from "react";
import http from "../api/http";
import '../css/Automobili.css';

export default function Automobili() {
  const [autos, setAutos] = useState([]);
  const [models, setModels] = useState([]);
  const [marks, setMarks] = useState([]);

  const [selectedMarkId, setSelectedMarkId] = useState("");
  const [filteredModels, setFilteredModels] = useState([]);
  const [selectedModelId, setSelectedModelId] = useState("");

  const [godiste, setGodiste] = useState("");
  const [cena, setCena] = useState("");
  const [gorivo, setGorivo] = useState("");
  const [menjac, setMenjac] = useState("");
  const [snaga, setSnaga] = useState("");
  const [kubikaza, setKubikaza] = useState("");
  const [kilometraza, setKilometraza] = useState("");
  const [slika, setSlika] = useState("");
  const [message, setMessage] = useState("");
  const [editingAutoId, setEditingAutoId] = useState(null);

  useEffect(() => {
    fetchAutos();
    fetchModels();
    fetchMarks();
  }, []);

  const fetchAutos = async () => {
    try {
      const res = await http.get("/automobil");
      setAutos(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchModels = async () => {
    try {
      const res = await http.get("/model");
      setModels(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchMarks = async () => {
    try {
      const res = await http.get("/marka");
      setMarks(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  // Kada se izabere marka → filtriramo modele te marke
  const handleMarkChange = (markId) => {
    setSelectedMarkId(markId);
    setSelectedModelId("");
    const filtered = models.filter((m) => m.marka?.id === parseInt(markId));
    setFilteredModels(filtered);
  };

  const handleSave = async () => {
  if (!selectedModelId) {
    setMessage("Morate izabrati model!");
    return;
  }

  try {
    const modelObj = models.find(m => m.id === parseInt(selectedModelId));

    const payload = {
      model: modelObj,
      godiste: parseInt(godiste),
      cena: parseFloat(cena),
      gorivo,
      menjac,
      snaga: parseInt(snaga),
      kubikaza: parseInt(kubikaza),
      kilometraza: parseInt(kilometraza),
      slika,
    };

    if (editingAutoId) {
      // UPDATE
      await http.put(`/automobil/${editingAutoId}`, payload);
      setMessage("Automobil je uspešno izmenjen!");
    } else {
      // CREATE
      await http.post("/automobil", payload);
      setMessage("Automobil je uspešno sačuvan!");
    }

    // reset forme
    setEditingAutoId(null);
    setSelectedMarkId("");
    setSelectedModelId("");
    setFilteredModels([]);
    setGodiste("");
    setCena("");
    setGorivo("");
    setMenjac("");
    setSnaga("");
    setKubikaza("");
    setKilometraza("");
    setSlika("");

    fetchAutos();
  } catch (e) {
    console.error(e);
    setMessage(
      e?.response?.data?.message ||
      "Došlo je do greške prilikom čuvanja automobila."
    );
  }
};

    const handleEdit = (auto) => {
        setEditingAutoId(auto.id);
        setSelectedMarkId(auto.model?.marka?.id || "");
        setSelectedModelId(auto.model?.id || "");
        
        // Popuni filteredModels prema marke
        const filtered = models.filter(m => m.marka?.id === auto.model?.marka?.id);
        setFilteredModels(filtered);

        setGodiste(auto.godiste);
        setCena(auto.cena);
        setGorivo(auto.gorivo);
        setMenjac(auto.menjac);
        setSnaga(auto.snaga);
        setKubikaza(auto.kubikaza);
        setKilometraza(auto.kilometraza);
        setSlika(auto.slika);
    };

  const handleDelete = async (id) => {
    try {
      await http.delete(`/automobil/${id}`);
      setMessage("Automobil obrisan.");
      fetchAutos();
    } catch (e) {
      console.error(e);
      setMessage("Greška prilikom brisanja automobila.");
    }
  };

  return (
    <div className="container">
      <h2>Automobili</h2>

      {message && <p className="message">{message}</p>}

      <div className="row-gap">
        {/* Marka */}
        <select
          value={selectedMarkId}
          onChange={(e) => handleMarkChange(e.target.value)}
        >
          <option value="">Izaberi marku</option>
          {marks.map((mark) => (
            <option key={mark.id} value={mark.id}>
              {mark.marka}
            </option>
          ))}
        </select>

        {/* Model */}
        <select
          value={selectedModelId}
          onChange={(e) => setSelectedModelId(e.target.value)}
          disabled={!selectedMarkId}
        >
          <option value="">
            {selectedMarkId ? "Izaberi model" : "Prvo izaberi marku"}
          </option>
          {filteredModels.map((m) => (
            <option key={m.id} value={m.id}>
              {m.model}
            </option>
          ))}
        </select>

        <input
          placeholder="Godiste"
          value={godiste}
          onChange={(e) => setGodiste(e.target.value)}
        />
        <input
          placeholder="Cena"
          value={cena}
          onChange={(e) => setCena(e.target.value)}
        />
        <input
          placeholder="Gorivo"
          value={gorivo}
          onChange={(e) => setGorivo(e.target.value)}
        />
        <input
          placeholder="Menjac"
          value={menjac}
          onChange={(e) => setMenjac(e.target.value)}
        />
        <input
          placeholder="Snaga"
          value={snaga}
          onChange={(e) => setSnaga(e.target.value)}
        />
        <input
          placeholder="Kubikaza"
          value={kubikaza}
          onChange={(e) => setKubikaza(e.target.value)}
        />
        <input
          placeholder="Kilometraza"
          value={kilometraza}
          onChange={(e) => setKilometraza(e.target.value)}
        />
        <input
          placeholder="URL slike"
          value={slika}
          onChange={(e) => setSlika(e.target.value)}
        />

        <button onClick={handleSave}>
        {editingAutoId ? "Sačuvaj izmene" : "Dodaj"}
        </button>
      </div>

      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead style={{ backgroundColor: "#f5f5f5" }}>
            <tr>
            <th>Marka</th>
            <th>Model</th>
            <th>Godište</th>
            <th>Cena</th>
            <th>Gorivo</th>
            <th>Menjač</th>
            <th>Snaga</th>
            <th>Kubikaža</th>
            <th>Kilometraža</th>
            <th>Slika</th>
            <th>Akcija</th>
            </tr>
        </thead>
        <tbody>
            {autos.map((a) => (
            <tr key={a.id}>
                <td>{a.model?.marka?.marka || "-"}</td>
                <td>{a.model?.model || "-"}</td>
                <td>{a.godiste}</td>
                <td>{a.cena}</td>
                <td>{a.gorivo}</td>
                <td>{a.menjac}</td>
                <td>{a.snaga}</td>
                <td>{a.kubikaza}</td>
                <td>{a.kilometraza}</td>
                <td>
                    {a.slika ? (
                        <a href={a.slika} target="_blank" rel="noopener noreferrer">
                        {a.slika}
                        </a>
                    ) : (
                        "-"
                    )}
                    </td>
                
                <td>
                    <button onClick={() => handleEdit(a)}>Izmeni</button>
                    <button onClick={() => handleDelete(a.id)}>Obriši</button>
                </td>
                
            </tr>
            ))}
        </tbody>
        </table>
    </div>
  );
}