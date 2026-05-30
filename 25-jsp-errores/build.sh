#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 25-jsp-errores..."
mvn clean package -pl 25-jsp-errores
