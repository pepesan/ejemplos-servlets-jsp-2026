#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Compilando 22-jsp-include-layout..."
mvn clean package -pl 22-jsp-include-layout
