#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 03-jsp-jstl..."
mvn test -pl 20-jsp-jstl
