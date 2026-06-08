#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Crear carpetas de datos si no existen
"$SCRIPT_DIR/00_init.sh"

docker compose -f "$SCRIPT_DIR/compose.yaml" up -d
echo "Servicios arrancados. Clientes web:"
echo "  phpMyAdmin  → http://localhost:8888"
echo "  Adminer     → http://localhost:8889"
