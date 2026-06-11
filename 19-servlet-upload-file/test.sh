#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 19-servlet-upload-file..."
mvn test -pl 19-servlet-upload-file
