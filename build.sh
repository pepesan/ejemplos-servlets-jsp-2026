#!/bin/bash
cd "$(dirname "$0")"
echo "Compilando todos los módulos..."
mvn clean package
