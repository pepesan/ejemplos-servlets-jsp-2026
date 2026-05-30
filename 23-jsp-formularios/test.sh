#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Tests de 23-jsp-formularios..."
mvn test -pl 23-jsp-formularios
