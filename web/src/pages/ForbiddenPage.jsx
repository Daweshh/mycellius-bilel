import { Link } from "react-router-dom";

export default function ForbiddenPage() {
 return (
 <div className="page-shell">
 <div className="topbar">
 <span className="brand">Mycellius</span>
 <Link className="btn btn-soft" to="/pages">Retour</Link>
 </div>
 <div className="panel">
 <h2 className="page-title">403 - Forbidden</h2>
 <p className="meta-line">Vous n'avez pas les droits pour cette action.</p>
 </div>
 </div>
 );
}