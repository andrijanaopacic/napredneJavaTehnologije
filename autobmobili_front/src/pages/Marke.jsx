import React, { useEffect, useState } from "react";
import http from "../api/http";

export default function Marke() {
  const [marks, setMarks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [novaMarka, setNovaMarka] = useState("");

  useEffect(() => { fetchMarks(); }, []);

  const fetchMarks = async () => {
    setLoading(true);
    try { 
      const res = await http.get("/marka");
      setMarks(res.data);
    } catch (e) { console.error(e); }
    setLoading(false);
  };

  const handleAdd = async () => {
    if(!novaMarka) return;
    await http.post("/marka", { marka: novaMarka });
    setNovaMarka("");
    fetchMarks();
  };

  const handleDelete = async (id) => {
    await http.delete(`/marka/${id}`);
    fetchMarks();
  };

  return (
    <div className="container">
      <h2>Marke</h2>
      <div className="row-gap">
        <input placeholder="Nova marka" value={novaMarka} onChange={e => setNovaMarka(e.target.value)} />
        <button onClick={handleAdd}>Dodaj</button>
      </div>
      {loading ? <p>Učitavanje...</p> : (
        <ul>
          {marks.map(m => (
            <li key={m.id}>{m.marka} <button onClick={() => handleDelete(m.id)}>Obriši</button></li>
          ))}
        </ul>
      )}
    </div>
  );
}