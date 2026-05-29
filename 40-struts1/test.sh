#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 05-struts1..."
mvn test -pl 40-struts1
