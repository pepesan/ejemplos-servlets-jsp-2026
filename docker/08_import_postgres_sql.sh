#!/usr/bin/env bash
set -euo pipefail

if [ $# -lt 1 ]; then
  echo "Uso: $0 <fichero.sql> [base_de_datos]"
  echo "  base_de_datos por defecto: cursosdb"
  exit 1
fi

SQL_FILE="$1"
DB="${2:-cursosdb}"

if [ ! -f "$SQL_FILE" ]; then
  echo "Error: no se encuentra el fichero '$SQL_FILE'"
  exit 1
fi

echo "Importando '$SQL_FILE' en PostgreSQL, base de datos '$DB'..."
docker exec -i cursosdev-postgres \
  psql -U curso -d "$DB" < "$SQL_FILE"
echo "Importación completada."
