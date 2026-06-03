import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function PageFormPage({ mode }) {
  const { id } = useParams();
  const { token, role, logout } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ id: "", title: "", content: "", tags: [] });
  const [tagsText, setTagsText] = useState("");
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!(role === "DEV" || role === "ADMIN")) navigate("/forbidden");
  }, [role, navigate]);

  useEffect(() => {
    async function load() {
      if (mode !== "edit") return;
      try {
        const res = await apiRequest(`/api/v1/pages/${id}`, { token });
        setForm({ ...res, tags: res.tags ?? [] });
        setTagsText((res.tags ?? []).map(t => t.name).join(", "));
      } catch (e) {
        if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
        if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
      }
    }
    load();
  }, [mode, id, token, logout, navigate]);

  async function onSubmit(e) {
    e.preventDefault();
    setError(null);
    const tags = tagsText
      .split(",")
      .map(s => s.trim())
      .filter(Boolean)
      .map(name => ({ name }));
    const payload = { ...form, tags };
    try {
      if (mode === "create") {
        await apiRequest("/api/v1/pages", { method: "POST", token, body: payload });
        navigate("/pages", { state: { flash: { type: "success", message: `Page ${form.id} créée` } } });
      } else {
        await apiRequest(`/api/v1/pages/${id}`, { method: "PUT", token, body: payload });
        navigate(`/pages/${id}`);
      }
    } catch (e) {
      if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
      if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
      setError(e instanceof ApiError ? JSON.stringify(e.body) : "Erreur enregistrement");
    }
  }

  return (
    <div className="page-shell">
      <div className="topbar">
        <span className="brand">Mycellius</span>
        <Link className="btn btn-soft" to={mode === "create" ? "/pages" : `/pages/${id}`}>Retour</Link>
      </div>
      <div className="panel">
      <h2 className="page-title">{mode === "create" ? "Creer" : "Editer"} une page</h2>
      <form onSubmit={onSubmit} className="form-grid">
        <input
          className="field"
          value={form.id}
          onChange={(e) => setForm({ ...form, id: e.target.value })}
          placeholder="id (ex: PAGE-123)"
          disabled={mode === "edit"}
        />
        <input
          className="field"
          value={form.title}
          onChange={(e) => setForm({ ...form, title: e.target.value })}
          placeholder="title"
        />
        <textarea
          className="field-area"
          value={form.content}
          onChange={(e) => setForm({ ...form, content: e.target.value })}
          placeholder="content"
          rows={8}
        />
        <input
          className="field"
          value={tagsText}
          onChange={(e) => setTagsText(e.target.value)}
          placeholder="tags (séparés par virgule)"
        />
        <button className="btn btn-primary" type="submit">Enregistrer</button>
      </form>
      {error && <p className="error-text">{error}</p>}
      </div>
    </div>
  );
}