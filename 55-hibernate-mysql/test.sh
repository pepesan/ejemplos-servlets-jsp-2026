#!/usr/bin/env bash
set -euo pipefail
# Requiere MySQL en marcha: cd ../docker && docker compose up -d mysql
cd "$(dirname "$0")/.."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" \
  mvn test -pl 55-hibernate-mysql
