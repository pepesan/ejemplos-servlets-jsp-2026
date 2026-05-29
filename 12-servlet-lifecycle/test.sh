#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 12-servlet-lifecycle..."
mvn test -pl 12-servlet-lifecycle
