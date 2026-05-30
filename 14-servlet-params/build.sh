#!/bin/bash
cd "$(dirname "$0")/.."
echo "Compilando 14-servlet-params..."
mvn clean package -pl 14-servlet-params
