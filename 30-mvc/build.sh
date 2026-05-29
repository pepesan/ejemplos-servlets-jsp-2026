#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 04-mvc..."
mvn clean package -pl 30-mvc
