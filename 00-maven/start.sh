#!/bin/bash
cd "$(dirname "$0")/.."
echo "Ejecutando 00-maven..."
mvn compile exec:java -pl 00-maven -Dexec.mainClass=com.cursosdedesarrollo.App -q
