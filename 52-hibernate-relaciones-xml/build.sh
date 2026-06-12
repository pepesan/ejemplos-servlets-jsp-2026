#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 52-hibernate-relaciones-xml..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn clean package -pl 52-hibernate-relaciones-xml
