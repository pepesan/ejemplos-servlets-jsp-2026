#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 15-servlet-filtros..."
mvn test -pl 15-servlet-filtros
