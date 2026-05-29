#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando demos de 06-hibernate..."
mvn test -pl 50-hibernate
