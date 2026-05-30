#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 15-servlet-filtros..."
mvn clean package -pl 15-servlet-filtros
