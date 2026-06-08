#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 53-hibernate-hql..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn clean package -pl 53-hibernate-hql
