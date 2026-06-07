#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 41-struts1-crud..."
mvn test -pl 41-struts1-crud
