#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Compilando 24-jsp-jstl-funciones..."
mvn clean package -pl 24-jsp-jstl-funciones
