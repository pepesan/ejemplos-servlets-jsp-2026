#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 01-maven..."
mvn clean package -pl 01-maven-dependencias
