#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 21-jsp-datos..."
mvn test -pl 21-jsp-datos
