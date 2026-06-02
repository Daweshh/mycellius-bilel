import React, { useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
export default function LoginPage() {
 const [username, setUsername] = useState("admin");
 const [password, setPassword] = useState("Admin123!");
 const [error, setError] = useState(null);
 const auth = useAuth();
 const navigate = useNavigate();
 async function onSubmit(e) {
 e.preventDefault();
 setError(null);
 try {
 const res = await apiRequest("/api/v1/auth/login", {
 method: "POST",
 body: { username, password },
 });
 auth.login(res);
 navigate("/pages");
 } catch (e) {
 if (e instanceof ApiError) setError(typeof e.body === "string" ? e.body : "Login failed");
 else setError("Login failed");
 }
 }
 return (
    <div className="page-shell">
      <div className="topbar">
        <span className="brand">Mycellius</span>
        <span className="topbar-meta">Pastel Edition</span>
      </div>
      <div className="panel">
        <h2 className="page-title">Mycellius - Login</h2>
        <p className="meta-line">Connecte-toi pour acceder a la base wiki.</p>
        <form onSubmit={onSubmit} className="form-grid" style={{ maxWidth: 360 }}>
          <input
            className="field"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="username"
          />
          <input
            className="field"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="password"
            type="password"
          />
          <button type="submit" className="btn btn-primary">Se connecter</button>
        </form>
        {error && <p className="error-text" style={{ marginTop: 12 }}>{String(error)}</p>}
      </div>
    </div>
  );
}