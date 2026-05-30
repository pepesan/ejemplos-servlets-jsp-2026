#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Compilando 23-jsp-formularios..."
mvn clean package -pl 23-jsp-formularios
