#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 54-hibernate-herencia-anotaciones..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn clean package -pl 54-hibernate-herencia-anotaciones
