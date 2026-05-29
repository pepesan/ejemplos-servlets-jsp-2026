#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 01-maven..."
mvn test -pl 02-maven-jar
