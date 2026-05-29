#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 06-hibernate..."
mvn clean package -pl 50-hibernate
