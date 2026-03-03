const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";
export class ApiError extends Error {
 constructor(status, body) {
 super(`API error ${status}`);
 this.status = status;
 this.body = body;
 }
}
export async function apiRequest(path, { method = "GET", token = null, body = null } = {}) {
 const headers = { "Content-Type": "application/json" };
 if (token) headers.Authorization = `Bearer ${token}`;
 const res = await fetch(`${API_URL}${path}`, {
 method,
 headers,
 body: body ? JSON.stringify(body) : null,
 });
 const text = await res.text();
 let data = null;
 try { data = text ? JSON.parse(text) : null; } catch { data = text; }
 if (!res.ok) throw new ApiError(res.status, data);
 return data;
}