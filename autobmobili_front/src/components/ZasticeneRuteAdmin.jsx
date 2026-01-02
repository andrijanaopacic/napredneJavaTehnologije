import React, { useContext } from "react";
import { AuthContext } from "./AuthContext";
import { Outlet } from "react-router-dom";

export default function ZasticeneRuteADMIN() {
  const { authed, me } = useContext(AuthContext);

  if (!authed || !me) {
    return <div style={{ padding: 40, textAlign: "center" }}>
      <h2>❌ Za pristup stranici morate biti prijavljeni kao admin. ❌</h2>
    </div>;
  }

  if (me.uloga !== "ADMIN") {
    return <div style={{ padding: 40, textAlign: "center" }}>
      <h2>❌ Nemate pravo pristupa ovoj stranici. ❌</h2>
    </div>;
  }

  return <Outlet />;
}