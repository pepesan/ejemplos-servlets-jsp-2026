#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 12-servlet-lifecycle..."
mvn verify -pl 12-servlet-lifecycle
