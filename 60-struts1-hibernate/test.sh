#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando tests de 60-struts1-hibernate..."
mvn test -pl 60-struts1-hibernate
