#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 14-servlet-params..."
mvn test -pl 14-servlet-params
