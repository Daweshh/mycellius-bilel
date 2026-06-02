import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate } from "react-router-dom";

export default function PagesListPage() {
  const { token, role, logout } = useAuth();
  const [pages, setPages] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    async function load() {
      try {
        // Variante A (Spring Page<>): /api/v1/pages?page=0&size=10
        // Variante B (liste simple): /api/v1/pages
        let res;
        try {
          res = await apiRequest("/api/v1/pages?page=0&size=10", { token });
        } catch {
          res = await apiRequest("/api/v1/pages", { token });
        }
        const content = Array.isArray(res) ? res : (res?.content ?? []);
        setPages(content);
      } catch (e) {
        if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
        if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
        setError("Erreur chargement pages");
      }
    }
    load();
  }, [token, logout, navigate]);

  async function onDelete(id) {
    if (!window.confirm(`Supprimer ${id} ?`)) return;
    try {
      await apiRequest(`/api/v1/pages/${id}`, { method: "DELETE", token });
      setPages(prev => prev.filter(p => p.id !== id));
    } catch (e) {
      if (e instanceof ApiError && e.status === 401) {
        logout();
        navigate("/login");
        return;
      }
      if (e instanceof ApiError && e.status === 403) {
        navigate("/forbidden");
        return;
      }
      setError(e instanceof ApiError ? JSON.stringify(e.body) : "Erreur suppression");
    }
  }

  return (
    <div className="page-shell">
      <div className="topbar">
        <span className="brand">Mycellius</span>
        <span className="topbar-meta">Role: {role}</span>
      </div>
      <div className="panel">
      <h2 className="page-title">Pages</h2>
      <div className="toolbar">
        {(role === "DEV" || role === "ADMIN") && (
          <Link className="btn btn-soft" to="/pages/new">
            Creer une page
          </Link>
        )}
        <button
          className="btn btn-primary"
          onClick={async () => {
            await logout();
            navigate("/login");
          }}
        >
          Deconnexion
        </button>
      </div>
      {error && <p className="error-text">{error}</p>}
      <ul className="list">
        {pages.map(p => (
          <li className="list-item" key={p.id}>
            <Link className="link" to={`/pages/${p.id}`}>{p.title} ({p.id})</Link>
            {(p.tags?.length ?? 0) > 0 && (
              <div className="tag-row">
                {p.tags.map((tag) => (
                  <span className="tag-pill" key={`${p.id}-${tag}`}>{tag}</span>
                ))}
              </div>
            )}
            {(role === "DEV" || role === "ADMIN") && (
              <button className="btn btn-soft" onClick={() => onDelete(p.id)}>
                Supprimer
              </button>
            )}
          </li>
        ))}
      </ul>
      </div>
    </div>
  );
}