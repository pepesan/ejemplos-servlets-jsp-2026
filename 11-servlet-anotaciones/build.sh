#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 11-servlet-anotaciones..."
mvn clean package -pl 11-servlet-anotaciones
