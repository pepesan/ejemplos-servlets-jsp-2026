#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 51-hibernate-anotaciones..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn clean package -pl 51-hibernate-anotaciones
