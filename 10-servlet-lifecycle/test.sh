#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 02-servlet-lifecycle..."
mvn test -pl 10-servlet-lifecycle
