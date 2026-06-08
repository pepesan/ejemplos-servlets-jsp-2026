#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 51-hibernate-anotaciones..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 51-hibernate-anotaciones
