#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 17-servlet-sesiones..."
mvn clean package -pl 17-servlet-sesiones
