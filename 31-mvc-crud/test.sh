#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 31-mvc-crud..."
mvn test -pl 31-mvc-crud
