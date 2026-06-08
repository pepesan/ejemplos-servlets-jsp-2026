#!/usr/bin/env bash
set -euo pipefail
DB="${1:-cursosdb}"
docker exec -it cursosdev-postgres \
  psql -U curso -d "$DB"
