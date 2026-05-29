#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 03-maven-profiles..."
mvn test -pl 03-maven-profiles
