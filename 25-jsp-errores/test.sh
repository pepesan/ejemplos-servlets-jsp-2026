#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 25-jsp-errores..."
mvn test -pl 25-jsp-errores
