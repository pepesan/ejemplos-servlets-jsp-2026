#!/bin/bash
cd "$(dirname "$0")/.."  
echo "Tests de 24-jsp-jstl-funciones..."
mvn test -pl 24-jsp-jstl-funciones
