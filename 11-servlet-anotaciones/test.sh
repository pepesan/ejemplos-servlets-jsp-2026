#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 11-servlet-anotaciones..."
mvn test -pl 11-servlet-anotaciones
