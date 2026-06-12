#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 54-hibernate-herencia-anotaciones..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 54-hibernate-herencia-anotaciones
