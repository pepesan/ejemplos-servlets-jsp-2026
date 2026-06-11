#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 47-struts1-upload..."
mvn test -pl 47-struts1-upload
