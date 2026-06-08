#!/usr/bin/env bash
set -euo pipefail
DB="${1:-cursosdb}"
docker exec -it cursosdev-mysql \
  mysql -ucurso -pcurso123 "$DB"
