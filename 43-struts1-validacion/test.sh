#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 43-struts1-validacion..."
mvn test -pl 43-struts1-validacion
