import React, { useState } from "react";

export default function CarNewsCard({ car }) {
  const [showDetails, setShowDetails] = useState(false);

  // Generiše random opis
  const generateRandomText = () => {
    const texts = [
      `Model ${car.marka} ${car.model} iz ${car.godiste} godine sa ${car.kubikaza}cc motora pruža vrhunsko iskustvo vožnje.`,
      `Upoznajte ${car.marka} ${car.model} ${car.godiste}: luksuz i snaga u savršenom spoju.`,
      `${car.marka} ${car.model} iz ${car.godiste} je idealan automobil za sve koji traže komfor i performanse.`,
      `Iskusite vožnju ${car.marka} ${car.model}, model ${car.godiste}, sa impresivnom snagom motora od ${car.kubikaza}cc.`,
      `${car.marka} ${car.model} ${car.godiste} – savršen balans stila i tehnologije.`
    ];
    return texts[Math.floor(Math.random() * texts.length)];
  };

  return (
    <div style={{
      background: "#1a1a1a",
      padding: "20px",
      borderRadius: "12px",
      width: "300px",
      color: "#fff",
      textAlign: "center",
      boxShadow: "0 0 15px rgba(0,0,0,0.5)"
    }}>
      <img src={car.img} alt={`${car.marka} ${car.model}`} style={{ width: "100%", borderRadius: "8px", marginBottom: "10px" }} />
      <h3 style={{ marginBottom: "8px", color:"#ff6f00" }}>{car.marka} {car.model}</h3>
      <p>Godište: {car.godiste} | Kubikaža: {car.kubikaza}cc</p>
      <button 
        style={{ marginTop: "10px", padding: "8px 16px", borderRadius: "6px", background:"#ff6f00", color:"#fff", border:"none", cursor:"pointer" }}
        onClick={() => setShowDetails(!showDetails)}
      >
        {showDetails ? "Sakrij detalje" : "Prikaži detalje"}
      </button>
      {showDetails && <p style={{ marginTop: "10px", fontSize:"0.9rem", lineHeight:"1.4rem" }}>{generateRandomText()}</p>}
    </div>
  );
}