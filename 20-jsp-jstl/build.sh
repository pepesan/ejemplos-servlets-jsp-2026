#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 03-jsp-jstl..."
mvn clean package -pl 20-jsp-jstl
