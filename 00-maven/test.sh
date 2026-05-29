#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 00-maven..."
mvn test -pl 00-maven
