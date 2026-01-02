import React, { useState } from "react";

const Pocetna = () => {
  const cars = [
    { 
      marka: "BMW", 
      model: "X5", 
      img: "https://cdn.motor1.com/images/mgl/kpY1B/s1/2022-bmw-x5-black-vermilion.jpg",
      tekst: `Od svih BMW-ovih modela u našoj zemlji najviše se prodaje X5. Omiljeni "bavarac" sada je dobio unapređenu verziju, a mi smo bili na premijeri održanoj u novom BMW salonu u Beogradu, gde smo saznali šta je sve promenjeno na ovom SUV-u, koja mu je sada cena, ali i jednu vrlo važnu vest kada je reč o upotrebi BMW Connected Drive sistema u Srbiji.

Najpopularniji BMW-ov SUV model X5 premijerno je predstavljen u Beogradu zajedno sa BMW Connected Drive sistemom, koji je od sada dostupan i u Srbiji. Prezentacija je održana u novootvorenom The BMW Store-u u Galeriji Beograd, koji se prostire na 500m² i pruža sve usluge jednog prodajnog salona.

Salon će biti namenjen najekskluzivnijim, novim modelima iz BMW ponude, a posetioci će moći i da se informišu o elektrifikaciji, kao i da konfigurišu svoja vozila uz pomoć prodajnog savetnika, ali i uz „virtuelnu realnost“ kako bi imali što verniji prikaz automobila. Dodatno, za ljubitelje BMW i MINI brenda selektovan je i široki izbor originalnih aksesoara, modnih dodataka i odevnih predmeta, kao i posebni artikli iz putnog programa.`
    },
    { 
      marka: "Audi", 
      model: "Q7", 
      img: "https://sharpmagazine.com/wp-content/uploads/2017/02/audi-sid-neigum-feat-1.jpg",
      tekst: `Audi Q7 je luksuzni SUV velikih dimenzija poznat po kombinaciji performansi, komfora i tehnologije, a dostupan je sa raznim V6 dizel motorima sa "blagom" hibridnom tehnologijom (MHEV). Karakteriše ga prostrana unutrašnjost, opciono sa sedam sedišta, kao i napredne tehničke karakteristike poput adaptivnog vazdušnog oslanjanja, multimedijalnog sistema i quattro pogona na sve točkove.`
    },
    { 
      marka: "Mercedes", 
      model: "GLE", 
      img: "https://imgcdn.oto.com.sg/large/gallery/exterior/8/176/mercedes-benz-gle-class-front-angle-low-view-783316.jpg",
      tekst: `Novi GLE je više od samo jedne unutrašnjosti: optimizovani dizajn prednjeg dela sa novom rešetkom hladnjaka naglašava njegov dinamični SUV karakter. Opciono dostupni 22-inčni naplatci od lake legure i svetao panoramski krov oličavaju visok nivo udobnosti vožnje čak i na prvi pogled. Dozvolite sebi da budete impresionirani prefinjenim detaljima: ambijentalno osvetljenje, animirani Mercedes-Benz uzorak koji se projektuje na tlo kada se otvore vrata. Unutrašnjost novog GLE-a inspiriše prvoklasnim komforom i visokokvalitetnim materijalima za Vaš odličan osećaj. Opremljen novim multifunkcionalnim upravljačem sa tri kraka i najnovijom generacijom MBUX-a, GLE nudi odličan komfor pri radu na svakom putovanju.`
    },
  ];

  const [selectedCar, setSelectedCar] = useState(null);

  return (
    <div style={{ 
      backgroundColor: "#0a0a0a", 
      minHeight: "100vh", 
      padding: "40px 20px", 
      display: "flex", 
      flexDirection: "column",
      alignItems: "center",
      color: "#fff"
    }}>
      <h1 style={{ marginBottom: "30px", color: "#ff6f00" }}>Pregled Automobila</h1>

      {/* Sekcija sa statistikama */}
      <section className="stats-section" style={{padding:"40px 0", textAlign:"center", color:"#fff", background:"#111", width:"100%", marginBottom:"40px"}}>
        <h2 style={{marginBottom:"20px"}}>Naša vozila u brojkama</h2>
        <div className="stats-grid" style={{display:"flex", justifyContent:"center", gap:"40px"}}>
          <div>
            <strong style={{fontSize:"1.8rem"}}>120+</strong>
            <p>Zadovoljnih korisnika</p>
          </div>
          <div>
            <strong style={{fontSize:"1.8rem"}}>300+</strong>
            <p>Automobila u ponudi</p>
          </div>
          <div>
            <strong style={{fontSize:"1.8rem"}}>50+</strong>
            <p>Luksuznih modela</p>
          </div>
        </div>
      </section>

      {/* Grid sa automobilima */}
      <div style={{ 
        display: "flex", 
        flexWrap: "wrap", 
        gap: "24px", 
        justifyContent: "center" 
      }}>
        {cars.map((car, index) => (
          <div 
            key={index} 
            style={{ 
              width: "370px", 
              cursor: "pointer", 
              border: "1px solid #444", 
              borderRadius: "8px", 
              overflow: "hidden",
              backgroundColor: "#111",
              textAlign: "center",
              paddingBottom: "10px"
            }}
          >
            <img 
              src={car.img} 
              alt={`${car.marka} ${car.model}`} 
              style={{ width: "100%", height: "200px", objectFit: "cover" }} 
            />
            <h3 style={{ margin: "10px 0", fontSize: "1.5rem" }}>{car.marka} {car.model}</h3>
            <button
              style={{ 
                padding: "10px 20px", 
                marginBottom: "10px",
                cursor: "pointer", 
                borderRadius: "5px", 
                background: "#ff6f00", 
                border: "none", 
                color: "#fff",
                fontSize: "1rem"
              }}
              onClick={() => setSelectedCar(car)}
            >
              Prikaži detalje
            </button>
          </div>
        ))}
      </div>

      {/* Modal */}
      {selectedCar && (
        <div 
          onClick={() => setSelectedCar(null)}
          style={{
            position: "fixed",
            top: 0, left: 0, right: 0, bottom: 0,
            backgroundColor: "rgba(0,0,0,0.8)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
            padding: "20px"
          }}
        >
          <div 
            onClick={(e) => e.stopPropagation()}
            style={{
              backgroundColor: "#222",
              padding: "20px",
              borderRadius: "10px",
              maxWidth: "800px",
              width: "100%",
              maxHeight: "90vh",
              overflowY: "auto",
              color: "#fff"
            }}
          >
            <img 
              src={selectedCar.img} 
              alt={`${selectedCar.marka} ${selectedCar.model}`} 
              style={{ width: "100%", height: "300px", objectFit: "cover", borderRadius: "8px" }} 
            />
            <h2 style={{ marginTop: "20px", marginBottom: "10px" }}>{selectedCar.marka} {selectedCar.model}</h2>
            <p style={{ lineHeight: "1.6", fontSize: "1.1rem" }}>{selectedCar.tekst}</p>
            <button 
              onClick={() => setSelectedCar(null)}
              style={{
                marginTop: "20px",
                padding: "10px 20px",
                background: "#ff6f00",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer",
                color: "#fff",
                fontSize: "1rem"
              }}
            >
              Zatvori
            </button>
          </div>
        </div>
      )}

    </div>
  );
};

export default Pocetna;