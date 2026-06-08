#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

mkdir -p "$SCRIPT_DIR/data/mysql"
mkdir -p "$SCRIPT_DIR/data/postgres"

echo "Carpetas de volúmenes creadas:"
echo "  $SCRIPT_DIR/data/mysql"
echo "  $SCRIPT_DIR/data/postgres"
