# Mycellius – Front web

## Lancer tout le projet en local

### 1) Base de donnees (MySQL via Docker)

```bash
cd infra/db
docker compose up -d
```

### 2) API Spring Boot

```bash
cd api
mvn spring-boot:run
```

API disponible sur `http://localhost:8080`.

### 3) Front web (Vite)

```bash
cd web
npm install
npm run dev
```

Par défaut, Vite démarre sur `http://localhost:5173`.
Le site est donc visible sur `http://localhost:5173`.

### Comptes de test (seed)

Ces comptes sont crees automatiquement par l'API au demarrage (si `mycellius.seed.enabled=true`).

| Username | Password | Role |
| --- | --- | --- |
| `admin` | `Admin123!` | `ADMIN` |
| `dev` | `Dev123!` | `DEV` |
| `stagiaire` | `Stagiaire123!` | `STAGIAIRE` |

## Rendu Markdown sécurisé

Le composant `SafeMarkdown` utilise `marked` pour parser le Markdown et `DOMPurify` pour nettoyer le HTML avant affichage.

## Scan de sécurité avec OWASP ZAP (TP11)

Une commande utilitaire est définie dans `package.json` sous le script `zap:baseline`.

1. Assurez-vous que le front est lancé en local (ex. `npm run dev` sur le port 5173).
2. Depuis le répertoire `web`, exécutez :

```bash
npm run zap:baseline
```

Cela lance le conteneur `owasp/zap2docker-stable` en mode baseline sur `http://host.docker.internal:5173` et génère un rapport `zap-report.html` dans le dossier `zap-report`.
