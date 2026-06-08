#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "⚠  Esto borrará todos los contenedores y los datos de MySQL y PostgreSQL."
read -r -p "¿Continuar? [s/N] " resp
[[ "$resp" =~ ^[sS]$ ]] || { echo "Cancelado."; exit 0; }

docker compose -f "$SCRIPT_DIR/compose.yaml" down --remove-orphans

if [ -d "$SCRIPT_DIR/data/mysql" ]; then
  sudo rm -rf "$SCRIPT_DIR/data/mysql"
  echo "Eliminado: $SCRIPT_DIR/data/mysql"
fi
if [ -d "$SCRIPT_DIR/data/postgres" ]; then
  sudo rm -rf "$SCRIPT_DIR/data/postgres"
  echo "Eliminado: $SCRIPT_DIR/data/postgres"
fi

echo "Listo. Los contenedores y datos han sido eliminados."
