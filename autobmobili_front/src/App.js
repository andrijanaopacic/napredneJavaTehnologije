import logo from './logo.svg';
import './App.css';
import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Pocetna from './pages/Pocetna';
import {Footer} from './components/Footer';
import Navbar from './components/Navbar';
import Oglasi from './pages/Oglasi';
import Marke from "./pages/Marke";
import Modeli from "./pages/Modeli";
import Automobili from "./pages/Automobili";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProtectedRoute from './components/ProtectedRoute';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from "./pages/ResetPassword";
import { AuthProvider } from './components/AuthContext';
import ZasticeneRuteADMIN from './components/ZasticeneRuteAdmin';
import ZasticeneRuteKORISNIK from './components/ZasticeneRuteKorisnik';
import OglasiKorisnik from "./pages/OglasiKorisnik";
import SacuvaniOglasi from "./pages/SacuvaniOglasi";


function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <div style={{paddingTop: "80px"}}>
          <Routes>
            {/* javne rute */}
            <Route path="/pocetna" element={<Pocetna />} />

            {/* rute za korisnika */}
            <Route element={<ZasticeneRuteKORISNIK />}>
              {/* ovde rute za običnog korisnika */}
              <Route element={<ZasticeneRuteKORISNIK />}>
              <Route path="/oglasi-korisnik" element={<OglasiKorisnik />} />
              <Route path="/sacuvani-oglasi" element={<SacuvaniOglasi />} />
              </Route>
            </Route>

            {/* rute za admina */}
            <Route element={<ZasticeneRuteADMIN />}>
              <Route path="/marke" element={<Marke />} />
              <Route path="/modeli" element={<Modeli />} />
              <Route path="/automobili" element={<Automobili />} />
              <Route path="/oglasi" element={<Oglasi />} />
            </Route>

            {/* auth rute */}
            <Route path="/login" element={<Login onSuccess={() => window.location.href='/pocetna'} />} />
            <Route path="/register" element={<Register onSuccess={() => window.location.href='/pocetna'} />} />
            <Route path="/forgot" element={<ForgotPassword />} />
            <Route path="/reset" element={<ResetPassword />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
