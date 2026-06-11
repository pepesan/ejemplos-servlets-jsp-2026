#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 45-struts1-excepciones..."
mvn test -pl 45-struts1-excepciones
