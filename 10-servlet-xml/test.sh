#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 10-servlet-xml..."
mvn test -pl 10-servlet-xml
