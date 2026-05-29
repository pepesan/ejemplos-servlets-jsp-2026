#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 13-servlet-request-response..."
mvn test -pl 13-servlet-request-response
