#!/bin/bash
set -e

echo "=== Déploiement Mycellius PROD ==="

cd /home/bilel/mycellius-bilel

echo "[1/6] Git pull..."
git pull

echo "[2/6] Configuration PROD..."
sed -i 's#localhost:3306/mycellius#localhost:3307/mycellius#g' api/src/main/resources/application.properties
sed -i 's#server.port=8080#server.port=8081#g' api/src/main/resources/application.properties
sed -i 's#http://192.168.30.154#http://192.168.30.152#g' api/src/main/resources/application.properties

echo "[3/6] Build backend..."
cd api
mvn clean package -DskipTests

echo "[4/6] Restart backend..."
sudo systemctl restart mycellius-prod

echo "[5/6] Build frontend..."
cd ../web
npm install
VITE_API_URL=http://192.168.30.152/mycellius npm run build -- --base=/mycellius/

echo "[6/6] Déploiement Nginx..."
rm -rf /var/www/mycellius/*
cp -r dist/* /var/www/mycellius/
sudo systemctl restart nginx

echo "=== Mycellius PROD déployé ==="
