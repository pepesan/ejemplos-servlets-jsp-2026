#!/bin/bash
cd "$(dirname "$0")/.."
PROFILE="${1:-dev}"
echo "Ejecutando App con profile: $PROFILE"
mvn compile exec:java -pl 03-maven-profiles -P "$PROFILE"
