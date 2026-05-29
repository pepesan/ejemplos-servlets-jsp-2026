#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 01-maven..."
mvn clean package -pl 02-maven-jar
