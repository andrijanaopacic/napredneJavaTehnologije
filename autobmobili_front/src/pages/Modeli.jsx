import React, { useEffect, useState } from "react";
import http from "../api/http";
import '../css/Modeli.css';

export default function Modeli() {
  const [models, setModels] = useState([]);
  const [marks, setMarks] = useState([]);
  const [noviModel, setNoviModel] = useState("");
  const [selectedMarkaId, setSelectedMarkaId] = useState("");

  useEffect(() => {
    fetchModels();
    fetchMarks();
  }, []);

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

  const handleAdd = async () => {
    if (!selectedMarkaId) {
      alert("Izaberite marku!");
      return;
    }
    if (!noviModel.trim()) {
      alert("Unesite naziv modela!");
      return;
    }

    const payload = {
      model: noviModel,
      marka: { id: parseInt(selectedMarkaId) }
    };

    try {
      await http.post("/model", payload);
      setNoviModel("");
      setSelectedMarkaId("");
      fetchModels();
    } catch (e) {
      console.error(e);
      alert(e?.response?.data?.message || "Greška prilikom dodavanja modela.");
    }
  };

  const handleDelete = async (id) => {
    try {
      await http.delete(`/model/${id}`);
      fetchModels();
    } catch (e) {
      console.error(e);
      alert("Greška prilikom brisanja modela.");
    }
  };

  return (
    <div className="container">
      <h2>Modeli</h2>

      <div className="row-gap">
        <select value={selectedMarkaId} onChange={e => setSelectedMarkaId(e.target.value)}>
          <option value="">Izaberi marku</option>
          {marks.map(mark => (
            <option key={mark.id} value={mark.id}>{mark.marka}</option>
          ))}
        </select>

        <input
          placeholder="Novi model"
          value={noviModel}
          onChange={e => setNoviModel(e.target.value)}
        />

        <button onClick={handleAdd}>Dodaj</button>
      </div>

      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse", marginTop: "20px" }}>
        <thead style={{ backgroundColor: "#ff8800", color: "#111" }}>
          <tr>
            <th>Marka</th>
            <th>Model</th>
          </tr>
        </thead>
        <tbody>
            {models.map(m => (
                <tr key={m.id}>
                <td>{m.marka?.marka || "-"}</td>
                <td>{m.model}</td>
                </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}