#!/bin/bash
cd "$(dirname "$0")"
echo "Ejecutando tests de todos los módulos..."
mvn test
