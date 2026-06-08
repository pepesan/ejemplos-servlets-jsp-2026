#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 52-hibernate-relaciones..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 52-hibernate-relaciones
