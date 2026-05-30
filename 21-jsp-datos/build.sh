#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 21-jsp-datos..."
mvn clean package -pl 21-jsp-datos
