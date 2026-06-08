#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 53-hibernate-hql..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn test -pl 53-hibernate-hql
