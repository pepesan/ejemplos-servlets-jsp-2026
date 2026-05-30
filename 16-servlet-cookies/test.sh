#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 16-servlet-cookies..."
mvn test -pl 16-servlet-cookies
