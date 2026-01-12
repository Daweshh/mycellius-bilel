# Séance 1 — Baseline cybersécurité (Bloc 3) : CIA + registre des risques
## 1) CIA (Confidentialité, Intégrité, Disponibilité)
### Confidentialité (C)
Objectif : empêcher l’accès aux données par des personnes non autorisées.
Mesures minimales (Sprint 0 / à venir) :
- Authentification (JWT) et rôles (RBAC) à partir de la séance sécurité.
- Secrets hors dépôt Git (pas de mots de passe dans le code, .env non commité).
- Accès serveur via SSH par clé (pas de mot de passe faible).
- HTTPS en production (plus tard).
Preuves :
- Clés SSH en place sur les VM.
- `.gitignore` + `.env.example` (si utilisé) et aucun secret commité.
### Intégrité (I)
Objectif : empêcher la modification non autorisée et garantir un état cohérent.
Mesures minimales :
- Validation côté serveur (à venir avec Spring).
- Migrations de base de données versionnées (Flyway/Liquibase, à venir).
- CI qui vérifie la structure et qui échouera si la base manque.
- Historique Git + PR dev→test→prod.
Preuves :
- Workflow Git (PR) actif.
- GitHub Actions “CI” en vert.
### Disponibilité (A)
Objectif : garder le service accessible et récupérable après incident.
Mesures minimales :
- 3 environnements séparés (dev/test/prod) : une casse n’impacte pas prod.
- Procédure de redémarrage services (nginx, docker).
- Sauvegarde/restauration (à venir avec MySQL).
Preuves :
- 3 VM accessibles et pages DEV/TEST/PROD.
- Commandes de restart connues/documentées.
---
## 2) Registre des risques (mini)
| Risque | Impact | Vraisemblance | Mesures de réduction | Preuve / contrôle |
|---|---:|---:|---|---|
| Secrets committés (token, mdp) | Fort | Moyen | `.env` non versionné, variables
d’environnement, relecture PR | `.gitignore`, revue PR, éventuel secret scanning |
| Compromission SSH (mot de passe faible) | Fort | Moyen | Auth par clé, désactiver password
login (option) | test connexion par clé, config ssh |
| Perte de données DB | Fort | Moyen | backups + test de restauration | procédure + preuve
test |
| Injection / XSS via entrées utilisateur | Fort | Moyen | validation serveur + encodage + tests
sécurité | tests, ZAP plus tard |
| Token JWT volé | Fort | Moyen | durée courte, rotation, stockage sécurisé (mobile) | config
JWT + SecureStore |
---
## 3) Décisions prises en séance 1 (Sprint 0)
- Branches : dev/test/prod avec PR obligatoires vers test/prod.
- 3 VM : 192.168.3.151 (DEV), 192.168.3.154 (TEST), 192.168.3.152 (PROD).
- “Preuve de déploiement” : nginx sert une page différente selon la branche. 
