#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando y ejecutando 01-maven..."
mvn compile exec:java -pl 01-maven-dependencias -q
