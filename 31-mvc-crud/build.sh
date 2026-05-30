#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 31-mvc-crud..."
mvn clean package -pl 31-mvc-crud
