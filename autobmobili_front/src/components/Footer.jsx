import React from "react";

export const Footer = () => {
  return (
    <footer style={{
      backgroundColor: "#0a0a0a",
      color: "#fff",
      padding: "24px 20px",
      marginTop: "40px",
      textAlign: "center"
    }}>
      <p style={{ margin: "4px 0" }}>© {new Date().getFullYear()} AutobMobili</p>
      <p style={{ margin: "4px 0", color: "#ff6f00" }}>Sva prava zadržana</p>
    </footer>
  )
}