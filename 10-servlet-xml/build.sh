#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 10-servlet-xml..."
mvn clean package -pl 10-servlet-xml
