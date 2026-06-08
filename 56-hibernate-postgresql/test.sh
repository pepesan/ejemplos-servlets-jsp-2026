#!/usr/bin/env bash
set -euo pipefail
# Requiere PostgreSQL en marcha: cd ../docker && docker compose up -d postgres
cd "$(dirname "$0")/.."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" \
  mvn test -pl 56-hibernate-postgresql
