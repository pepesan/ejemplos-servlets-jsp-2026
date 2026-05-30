#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 17-servlet-sesiones..."
mvn test -pl 17-servlet-sesiones
