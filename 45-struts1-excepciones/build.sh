#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 45-struts1-excepciones..."
mvn clean package -pl 45-struts1-excepciones
