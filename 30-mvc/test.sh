#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 04-mvc..."
mvn test -pl 30-mvc
