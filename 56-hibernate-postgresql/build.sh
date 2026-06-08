#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" \
  mvn clean package -pl 56-hibernate-postgresql -DskipTests
