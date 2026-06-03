import React, { useEffect, useState } from "react";
import { apiRequest, ApiError } from "../api/apiClient";
import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate, useParams } from "react-router-dom";
import SafeMarkdown from "../components/SafeMarkdown";

export default function PageDetailPage() {
 const { id } = useParams();
 const { token, role, logout } = useAuth();
 const [page, setPage] = useState(null);
 const navigate = useNavigate();
 useEffect(() => {
 async function load() {
 try {
 const res = await apiRequest(`/api/v1/pages/${id}`, { token });
 setPage(res);
 } catch (e) {
 if (e instanceof ApiError && e.status === 401) { logout(); navigate("/login"); return; }
 if (e instanceof ApiError && e.status === 403) { navigate("/forbidden"); return; }
 }
 }
 load();
 }, [id, token, logout, navigate]);
 if (!page) return <div className="panel">Chargement...</div>;
 return (
 <div className="page-shell">
 <div className="topbar">
 <span className="brand">Mycellius</span>
 <Link className="btn btn-soft" to="/pages">Retour a la liste</Link>
 </div>
 <div className="panel">
 <h2 className="page-title">{page.title}</h2>
 <p className="meta-line"><b>ID</b> : {page.id}</p>
      {(page.tags?.length ?? 0) > 0 && (
        <div className="tag-row">
          {page.tags.map((tag) => (
            <span className="tag-pill" key={`${page.id}-${tag}`}>{tag}</span>
          ))}
        </div>
      )}
      <div className="markdown-card">
        <SafeMarkdown markdown={page.content} />
      </div>
 {(role === "DEV" || role === "ADMIN") && (
 <p><Link className="btn btn-soft" to={`/pages/${page.id}/edit`}>Editer</Link></p>
 )}
 </div>
 </div>
 );
}