#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 00-maven..."
mvn clean package -pl 00-maven
